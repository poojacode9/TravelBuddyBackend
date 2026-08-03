package com.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.dto.BookingDTO;
import com.travel.dto.TicketDTO;
import com.travel.dto.payment.BookingPaymentResponseDTO;
import com.travel.dto.payment.CreateOrderRequestDTO;
import com.travel.dto.payment.CreateOrderResponseDTO;
import com.travel.entity.Booking;
import com.travel.entity.TourPackage;
import com.travel.entity.User;
import com.travel.enums.BookingStatus;
import com.travel.repository.BookingRepository;
import com.travel.repository.TourPackageRepository;
import com.travel.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.client.RestTemplate;
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourPackageRepository tourPackageRepository;
    private final RestTemplate restTemplate;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            TourPackageRepository tourPackageRepository,
            RestTemplate restTemplate) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public BookingPaymentResponseDTO  createBooking(BookingDTO bookingDTO) {

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TourPackage tourPackage = tourPackageRepository.findById(bookingDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        Booking booking = new Booking();
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setTravelDate(bookingDTO.getTravelDate());
        booking.setNumberOfPersons(bookingDTO.getNumberOfPersons());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        if (tourPackage.getAvailableSeats() < bookingDTO.getNumberOfPersons()) {
            throw new RuntimeException("Not enough seats available");
        }

        

        booking.setStatus(BookingStatus.PENDING);
        booking.setUser(user);
        booking.setTourPackage(tourPackage);

        Booking savedBooking = bookingRepository.save(booking);
        CreateOrderRequestDTO paymentRequest =
                new CreateOrderRequestDTO(
                        savedBooking.getId(),
                        user.getId(),
                        savedBooking.getTotalAmount());

        CreateOrderResponseDTO paymentResponse =
                restTemplate.postForObject(
                        "http://localhost:8081/payments/create-order",
                        paymentRequest,
                        CreateOrderResponseDTO.class);

        return new BookingPaymentResponseDTO(
                mapToDTO(savedBooking),
                paymentResponse
        );
    }

    @Override
    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return mapToDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TourPackage tourPackage = tourPackageRepository.findById(bookingDTO.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setTravelDate(bookingDTO.getTravelDate());
        booking.setNumberOfPersons(bookingDTO.getNumberOfPersons());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setStatus(bookingDTO.getStatus());
        booking.setUser(user);
        booking.setTourPackage(tourPackage);

        Booking updatedBooking = bookingRepository.save(booking);

        return mapToDTO(updatedBooking);
    }

    @Override
    public String deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingRepository.delete(booking);

        return "Booking deleted successfully";
    }

    private BookingDTO mapToDTO(Booking booking) {

        return new BookingDTO(
                booking.getId(),
                booking.getBookingDate(),
                booking.getTravelDate(),
                booking.getNumberOfPersons(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getUser().getId(),
                booking.getTourPackage().getId());
    }
    
    
    @Override
    public List<BookingDTO> getBookingsByUser(Long userId) {

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    @Override
    public List<BookingDTO> getBookingsByGuide(Long guideId) {

        return bookingRepository.findByTourPackageGuideId(guideId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    @Override
    public List<BookingDTO> getBookingsByPackage(Long packageId) {

        return bookingRepository.findByTourPackageId(packageId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
 
    
    @Override
    public BookingDTO cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        TourPackage tourPackage = booking.getTourPackage();

        tourPackage.setAvailableSeats(
                tourPackage.getAvailableSeats() + booking.getNumberOfPersons());

        tourPackageRepository.save(tourPackage);

        booking.setStatus(BookingStatus.CANCELLED);

        return mapToDTO(bookingRepository.save(booking));
    }
    
    @Transactional
    @Override
    public BookingDTO confirmBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            return mapToDTO(booking);
        }

        TourPackage tourPackage = booking.getTourPackage();

        if (tourPackage.getAvailableSeats() < booking.getNumberOfPersons()) {
            throw new RuntimeException("Not enough seats available");
        }

        tourPackage.setAvailableSeats(
                tourPackage.getAvailableSeats() - booking.getNumberOfPersons());

        tourPackageRepository.save(tourPackage);

        booking.setStatus(BookingStatus.CONFIRMED);

        return mapToDTO(bookingRepository.save(booking));
    }
    
    
    
    @Override
    public TicketDTO generateTicket(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Ticket cannot be generated until payment is completed.");
        }

        return new TicketDTO(
                "TICKET-" + booking.getId(),   // ticketNumber
                booking.getId(),               // bookingId
                booking.getUser().getName(),   // customerName
                booking.getUser().getEmail(),  // email
                booking.getTourPackage().getPackageName(),
                booking.getTourPackage().getDestination(),
                booking.getTravelDate().toString(),
                booking.getNumberOfPersons(),
                booking.getTotalAmount(),
                booking.getStatus().name()
        );
    }
}
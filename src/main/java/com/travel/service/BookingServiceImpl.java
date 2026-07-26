package com.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.dto.BookingDTO;
import com.travel.entity.Booking;
import com.travel.entity.TourPackage;
import com.travel.entity.User;
import com.travel.enums.BookingStatus;
import com.travel.repository.BookingRepository;
import com.travel.repository.TourPackageRepository;
import com.travel.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourPackageRepository tourPackageRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              TourPackageRepository tourPackageRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {

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

        tourPackage.setAvailableSeats(
                tourPackage.getAvailableSeats() - bookingDTO.getNumberOfPersons());

        tourPackageRepository.save(tourPackage);

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setUser(user);
        booking.setTourPackage(tourPackage);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToDTO(savedBooking);
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
}
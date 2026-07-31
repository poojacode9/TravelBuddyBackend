package com.travel.service;

import java.util.List;

import com.travel.dto.LoginRequestDTO;
import com.travel.dto.LoginResponseDTO;
import com.travel.dto.UserDTO;
import com.travel.dto.UserResponseDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    String deleteUser(Long id);
    
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    
    UserResponseDTO getCurrentUser();
}
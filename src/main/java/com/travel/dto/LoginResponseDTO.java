package com.travel.dto;

import com.travel.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String message;
    private String email;
    private Role role;

}
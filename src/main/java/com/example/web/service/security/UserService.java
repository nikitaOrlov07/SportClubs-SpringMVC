package com.example.web.service.security;

import com.example.web.dto.security.RegistrationDto;
import com.example.web.models.security.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}

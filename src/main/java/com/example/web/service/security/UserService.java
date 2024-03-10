package com.example.web.service.security;

import com.example.web.dto.security.RegistrationDto;
import com.example.web.models.Club;
import com.example.web.models.security.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
    List<Club> findClubsByUser(String  username);
}

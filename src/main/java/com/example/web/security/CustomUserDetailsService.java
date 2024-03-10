package com.example.web.security;

import com.example.web.models.security.UserEntity;
import com.example.web.repository.security.UserRepository;
import com.example.web.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
private UserRepository userRepository;

@Autowired
public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // configure "loadByUsername"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//Он загружает пользователя по имени пользователя.
        // Если пользователь найден, метод возвращает объект UserDetails, который представляет пользователя в контексте Spring Security.
        // Если пользователь не найден, метод выбрасывает исключение UsernameNotFoundException.
        UserEntity userEntity=userRepository.findFirstByUsername(username);// если нету "First" --> вернет больше одного пользователя
        if( userEntity != null)
        {
            User  authUser= new User(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())

            );//класс User взят из Spring-Security (мы не делали этот класс)
            return authUser;
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }


}

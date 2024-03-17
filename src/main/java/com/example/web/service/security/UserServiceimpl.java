package com.example.web.service.security;

import com.example.web.dto.security.RegistrationDto;
import com.example.web.models.Club;
import com.example.web.models.Comment;
import com.example.web.models.security.RoleEntity;
import com.example.web.models.security.UserEntity;
import com.example.web.repository.ClubRepository;
import com.example.web.repository.CommentRepository;
import com.example.web.repository.security.RoleRepository;
import com.example.web.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService{
    private UserRepository userRepository; private RoleRepository roleRepository;  // implements methods from repositories
    private PasswordEncoder passwordEncoder; private ClubRepository clubRepository; private CommentRepository commentRepository;

    @Autowired
    public UserServiceimpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder, ClubRepository clubRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.clubRepository = clubRepository;
        this.commentRepository =commentRepository;
    } // Technically we don`t need a constructor , but it is good practice

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity(); // we cant save RegistrationDto to the database because it`s totally different entity
        // create something like mapper
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userEntity.setTown(registrationDto.getTown());
        userEntity.setPhoneNumber(registrationDto.getPhoneNumber());

        RoleEntity role = roleRepository.findByName("USER");// по факту "USER"  записывается в переменную role (- В этой строке мы ищем объект RoleEntity, представляющий роль "USER" в системе.)
        userEntity.setRoles(Arrays.asList(role));// даем нашему userEntity (юзеру) роль "USER" (мы назначаем найденную роль "USER" пользователю, устанавливая список ролей пользователя в качестве списка,
        // содержащего только одну роль "USER".)
        //----------------------------------------------------------------
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
       return  userRepository.findByUsername(username);
    }
    @Override
    public UserEntity findById(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }
    @Override
    public List<Club> findClubsByUser(String  username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return clubRepository.findAllByCreatedBy_Id(userEntity.getId());
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long userId) {
        List<Club> clubs = clubRepository.findAllByCreatedBy_Id(userId);
        for (Club club : clubs) {
            List<Comment> comments = commentRepository.findAllByClubId(club.getId());
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
            clubRepository.delete(club);
        }
        userRepository.deleteUserRole(userId);
        userRepository.deleteById(userId);
    }




}

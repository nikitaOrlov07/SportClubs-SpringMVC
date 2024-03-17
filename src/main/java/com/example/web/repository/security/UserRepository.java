package com.example.web.repository.security;

import com.example.web.models.security.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findFirstByUsername(String username);
    Optional<UserEntity> findById(Long userId);
    @Modifying // запрос меняет состояние датабазы
    @Transactional // транзакционное выполнение запроса
    @Query(value = "DELETE FROM users_role WHERE user_id = :userId", nativeQuery = true)
    void deleteUserRole(@Param("userId") Long userId);



}

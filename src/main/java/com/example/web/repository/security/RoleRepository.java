package com.example.web.repository.security;

import com.example.web.models.security.RoleEntity;
import com.example.web.models.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}

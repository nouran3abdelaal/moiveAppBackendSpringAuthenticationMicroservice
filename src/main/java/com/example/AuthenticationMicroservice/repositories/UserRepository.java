package com.example.AuthenticationMicroservice.repositories;

import com.example.AuthenticationMicroservice.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}

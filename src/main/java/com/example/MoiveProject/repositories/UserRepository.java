package com.example.MoiveProject.repositories;

import com.example.MoiveProject.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}

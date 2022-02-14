package com.example.time.repository;

import com.example.time.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    Optional<User> findById(Long id);
}

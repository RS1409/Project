package com.app.repository;

import com.app.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByEmail(String email);

    List<User> findByUsernameContaining(String pattern);
    List<User> findAllByUsernameContaining(String pattern, Pageable pageable);
}

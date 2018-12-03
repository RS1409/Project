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

    List<User> findByFirstNameContaining(String pattern);
    List<User> findByLastNameContaining(String pattern);
    List<User> findAllByFirstNameContaining(String pattern, Pageable pageable);
    List<User> findAllByLastNameContaining(String pattern, Pageable pageable);

}

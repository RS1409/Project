package com.app.repository;

import com.app.model.Preference;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository  extends JpaRepository<Preference, Long> {
    Preference findByUser(User user);
}

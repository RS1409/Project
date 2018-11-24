package com.app.repository;

import com.app.model.FriendRequest;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    void deleteById(Long id);
    Set<FriendRequest> findAllByTo(User User);
    Set<FriendRequest> findAllByFrom(User User);

}


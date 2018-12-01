package com.app.repository;

import com.app.model.Conversation;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    public Conversation findByFirstUserAndSecondUser(User firstUser, User secondUser);
}

package com.app.repository;

import com.app.model.Conversation;
import com.app.model.ConversationNotification;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<ConversationNotification, Long>{
    public ConversationNotification findFirstByUserAndConversation(User user, Conversation conversation);
}

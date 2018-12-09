package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.Conversation;
import com.app.model.ConversationNotification;
import com.app.model.Message;
import com.app.model.User;
import com.app.repository.ConversationRepository;
import com.app.repository.MessageRepository;
import com.app.repository.NotificationRepository;
import com.app.repository.UserRepository;
import com.app.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.Notification;

@Controller
public class ConversationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/startConversation")
    private String startConversation(@AuthenticationPrincipal User loggedUser, Long userId, Model model)
    {
        User user = userRepository.findById(userId).get();
        Conversation conversation = conversationRepository.findByFirstUserAndSecondUser(loggedUser, user);
        if(conversation == null) conversation = conversationRepository.findByFirstUserAndSecondUser(user, loggedUser);
        if(conversation == null){
            conversation = new Conversation();
            conversation.setFirstUser(loggedUser);
            conversation.setSecondUser(user);
            conversationRepository.save(conversation);
        }

        ConversationNotification cn = notificationRepository.findFirstByUserAndConversation(loggedUser, conversation);
        if(cn!=null)
        {
            loggedUser.getNotifications().remove(cn);
            notificationRepository.delete(cn);
        }

        model.addAttribute("conversation",conversation);
        model.addAttribute("user", new UserDTO(loggedUser));
        model.addAttribute("user2", new UserDTO(user));
        model.addAttribute("content", "chat");
        return "homepage";
    }

    @PostMapping("/sendMessage")
    private String sendMessage(@AuthenticationPrincipal User loggedUser,
                               Long conversationId, String message,
                               Model model)
    {
        Conversation conversation = conversationRepository.findById(conversationId).get();
        Message userMessage = new Message(loggedUser, message, DateTimeService.getCurrentTime());

        User user = (conversation.getFirstUser().equals(loggedUser)) ? conversation.getSecondUser() : conversation.getFirstUser();
        System.out.println(user.getUsername());

        String fromUser = loggedUser.getFirstName() + " " + loggedUser.getLastName();
        ConversationNotification cn = new ConversationNotification(user, conversation, fromUser, loggedUser.getId());
        if(!user.getNotifications().contains(cn)){
            user.addConversationNotification(cn);
            conversation.addConversationNotification(cn);
        }
        notificationRepository.save(cn);
        userRepository.save(user);
        messageRepository.save(userMessage);
        conversation.addMessage(userMessage);
        conversationRepository.save(conversation);

        model.addAttribute("conversation",conversationRepository.findById(conversationId).get());
        model.addAttribute("user", new UserDTO(loggedUser));
        model.addAttribute("user2", new UserDTO(user));
        model.addAttribute("content", "chat");
        return "homepage";
    }
}

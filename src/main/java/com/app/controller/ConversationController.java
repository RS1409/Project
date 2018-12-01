package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.Conversation;
import com.app.model.Message;
import com.app.model.User;
import com.app.repository.ConversationRepository;
import com.app.repository.MessageRepository;
import com.app.repository.UserRepository;
import com.app.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConversationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

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
        model.addAttribute("conversation",conversation);
        model.addAttribute("user", new UserDTO(loggedUser));
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



        messageRepository.save(userMessage);
        conversation.addMessage(userMessage);
        conversationRepository.save(conversation);

        model.addAttribute("conversation",conversationRepository.findById(conversationId).get());
        model.addAttribute("user", new UserDTO(loggedUser));
        model.addAttribute("content", "chat");
        System.out.println("TO TEGO DZIALA");
        return "homepage";
    }
}

package com.app.controller;

import com.app.model.FriendRequest;
import com.app.model.User;
import com.app.repository.FriendRequestRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FriendController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;



    @GetMapping("/addFriend")
    private String addFriend(@RequestParam Long userId, @AuthenticationPrincipal User loggedUser)
    {

        FriendRequest friendRequest = new FriendRequest();
        Optional<User> repoSearchResult = userRepository.findById(userId);
        User user = repoSearchResult.get();

        friendRequest.setFrom(loggedUser);
        friendRequest.setTo(user);
        friendRequest.setStatus("Sended");

        friendRequestRepository.save(friendRequest);

        return "redirect:/users/"+userId;
    }

    @PostMapping(value="/requestConfirmation", params = "Accept")
    public String acceptFriend(@RequestParam Long friendRequestId, @AuthenticationPrincipal User loggedUser) {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).get();
        friendRequest.setStatus("Accepted");
        friendRequestRepository.save(friendRequest);

        return "redirect:/myFriends";
    }

    @PostMapping(value="/requestConfirmation", params = "Decline")
    public String declineFriend(@RequestParam Long friendRequestId, @AuthenticationPrincipal User loggedUser) {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).get();
        friendRequestRepository.delete(friendRequest);
        return "redirect:/myFriends";
    }
}

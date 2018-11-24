package com.app.controller;

import com.app.model.FriendRequest;
import com.app.model.User;
import com.app.repository.FriendRequestRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println("Entering method addFriend");
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setFromUser(loggedUser.getId());
        friendRequest.setToUser(userId);
        friendRequest.setMessage("Add me to friendList");

        Optional<User> repoSearchResult = userRepository.findById(userId);
        User user = repoSearchResult.get();

        if(user.getFriendRequests().contains(friendRequest))
        {
            System.out.println("Friend request exists");
        }
        else{
            user.addRequestToUser(friendRequest);
            userRepository.save(user);
        }
        return "redirect:/users/"+userId;
    }

    @GetMapping(value="/requestConfirmation", params = "Accept")
    public String acceptFriend(@RequestParam Long friendRequestId, @AuthenticationPrincipal User loggedUser) {
        System.out.println("Processing friend requese with id" + friendRequestId);
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).get();
        System.out.println(friendRequest);

        Optional<User> userRequest = userRepository.findById(friendRequest.getFromUser());
        User user = userRequest.get();
        System.out.println(user.getUsername());

        loggedUser.addFriendToUser(user);
        loggedUser.getFriendRequests().remove(friendRequest);

        System.out.println("SAVING USERS");
        userRepository.save(loggedUser);

        return "redirect:/myFriends";
    }

    @GetMapping(value="/requestConfirmation", params = "Decline")
    public String declineFriend(@RequestParam Long friendRequestId, @AuthenticationPrincipal User loggedUser) {

        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).get();
        loggedUser.getFriendRequests().remove(friendRequest);

        friendRequestRepository.delete(friendRequest);
        userRepository.save(loggedUser);
        return "redirect:/myFriends";
    }
}

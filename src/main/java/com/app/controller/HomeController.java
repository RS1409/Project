package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.*;
import com.app.repository.CommentRepository;
import com.app.repository.FriendRequestRepository;
import com.app.repository.PostRepository;
import com.app.repository.UserRepository;
import com.app.service.DateTimeService;
import com.app.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping("/")
    public String redirectHome()
    {
        return "redirect:/mypage";
    }

    @GetMapping("/mypage")
    public String getHome(@AuthenticationPrincipal User loggedUser, Model model) {
        if(!friendRequestRepository.findByToAndStatus(loggedUser, FriendRequest.Status.REQUESTED).isEmpty())
        {
            loggedUser.setNewRequests(true);
            userRepository.save(loggedUser);
        } else {
            loggedUser.setNewRequests(false);
            userRepository.save(loggedUser);
        }

        User user = userRepository.findByUsername(loggedUser.getUsername());
        UserDTO userDTO = new UserDTO(user);

        model.addAttribute("user", userDTO);
        model.addAttribute("content", "posts");
        return "homepage";
    }


    @PostMapping("/addPost")
    private String addPost(@AuthenticationPrincipal User user,
                           @RequestParam String postContent) {
        if(postContent.length() != 0)
        {
            Post post = new Post(postContent, DateTimeService.getCurrentTime());
            user.addPostToUser(post);
            System.out.println(post);
            System.out.println(user.getPosts().size());

            postRepository.save(post);

            Set<ConversationNotification> emptyNotifications = new HashSet<>();
            user.setNotifications(emptyNotifications);
            userRepository.save(user);

            return "redirect:/mypage";
        } else return "redirect:/mypage";
    }

    @PostMapping("/commentPost")
    private String addComment(@RequestParam Long postId,
                              @RequestParam String commentContent,
                              @AuthenticationPrincipal User user)
    {
        if(commentContent.length() != 0)
        {
            String authorName = user.getFirstName() + ' ' + user.getLastName();
            Comment comment = new Comment(commentContent, DateTimeService.getCurrentTime(), authorName);
            Post post = postRepository.getOne(postId);

            post.addCommentToPost(comment);
            post.addCommentCounter();
            commentRepository.save(comment);
            postRepository.save(post);

        return "redirect:/mypage";
        } else return "redirect:/mypage";

    }

    @GetMapping("/myFriends")
    private String showFriends(@AuthenticationPrincipal User user, Model model)
    {

        UserDTO userDTO = new UserDTO(userRepository.findByUsername(user.getUsername()));
        Set<FriendRequest> friendRequests = friendRequestRepository.findAllByTo(user);
        friendRequests.addAll(friendRequestRepository.findAllByFrom(user));
        System.out.println("Total size of requests" + friendRequests.size());

        Set<FriendRequest> received = friendRequestService.filterRequested(friendRequests);
        System.out.println("Size of received requests" + received.size());
        received.stream().forEach(System.out::println);

        Set<FriendRequest> accepted = friendRequestService.filterAccepted(friendRequests);
        System.out.println("Size of accepted requests" + accepted.size());

        model.addAttribute("user", userDTO);
        model.addAttribute("content","friends");
        model.addAttribute("received", received);
        model.addAttribute("accepted", accepted);
        return "homepage";
    }

    @GetMapping("/myPreferences")
    public String getMyPreferences(@AuthenticationPrincipal User loggedUser, Model model,
                                   @ModelAttribute String content) {
        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "myPreferences");
        return "homepage";
    }
}

package com.app.controller;


import com.app.dto.UserDTO;
import com.app.model.Comment;
import com.app.model.FriendRequest;
import com.app.model.Post;
import com.app.model.User;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping("{id}")
    private String showUserPage(@PathVariable Long id, @AuthenticationPrincipal User loggedUser, Model model)
    {
        Optional<User> user = userRepository.findById(id);
        UserDTO loggedUserDTO = new UserDTO(loggedUser);
        UserDTO pageOwnerDTO = new UserDTO(user.get());

        Set<FriendRequest> usersRequestsTo = friendRequestRepository.findAllByTo(user.get());
        Set<FriendRequest> usersRequestsFrom = friendRequestRepository.findAllByFrom(user.get());

        boolean requestToExists = (usersRequestsTo.stream().anyMatch(x->x.getFrom().getId().equals(loggedUser.getId())));
        boolean requestFromExists = (usersRequestsFrom.stream().anyMatch(x->x.getTo().getId().equals(loggedUser.getId())));
        boolean requestExists = (requestFromExists || requestToExists);


        if(pageOwnerDTO.getId().equals(loggedUserDTO.getId()))
            return "redirect:/mypage";

        model.addAttribute("user", pageOwnerDTO);
        model.addAttribute("loggedUser", loggedUserDTO);
        model.addAttribute("friends", requestExists);
        return "userPage";
    }

    @PostMapping("/commentPost")
    public String commentPost(@RequestParam Long postId,
                              @RequestParam Long userId,
                              @RequestParam String commentContent,
                              @AuthenticationPrincipal User user)
    {
        System.out.println("Adding Comment");
        if(commentContent.length() != 0)
        {
            Comment comment = new Comment(commentContent, DateTimeService.getCurrentTime(), user.getUsername());
            Post post = postRepository.getOne(postId);
            post.addCommentToPost(comment);
            post.addCommentCounter();

            commentRepository.save(comment);
            postRepository.save(post);
            return "redirect:/users/"+userId;
        } else return "redirect:/users/"+userId;
    }
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepo;


    @GetMapping("/")
    public String redirectHome()
    {
        return "redirect:/mypage";
    }

    @GetMapping("/mypage")
    public String getHome(@AuthenticationPrincipal User loggedUser, Model model) {

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
            Comment comment = new Comment(commentContent, DateTimeService.getCurrentTime(), user.getUsername());
            Post post = postRepository.getOne(postId);
            post.addCommentToPost(comment);
            post.addCommentCounter();

            commentRepo.save(comment);
            postRepository.save(post);
        return "redirect:/mypage";
        } else return "redirect:/mypage";
    }

    @GetMapping("/myFriends")
    private String showFriends(@AuthenticationPrincipal User user, Model model)
    {
        UserDTO userDTO = new UserDTO(userRepository.findByUsername(user.getUsername()));
        System.out.println("Friend Requests: " + user.getFriendRequests().size());
        model.addAttribute("content", "friends");
        model.addAttribute("user", userDTO);
        return "homepage";
    }
}

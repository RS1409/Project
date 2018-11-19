package com.app.controller;


import com.app.model.Comment;
import com.app.model.Post;
import com.app.model.User;
import com.app.repository.CommentRepository;
import com.app.repository.PostRepository;
import com.app.repository.UserRepository;
import com.app.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("{id}")
    private String showUserPage(@PathVariable Long id, Model model)
    {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
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

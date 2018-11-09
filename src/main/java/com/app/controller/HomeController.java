package com.app.controller;

import com.app.model.Post;
import com.app.model.User;
import com.app.repository.PostRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PostRepository postRepo;


    @GetMapping("/")
    public String redirectHome()
    {
        return "redirect:/mypage";
    }

    @GetMapping("/mypage")
    public String getHome(@AuthenticationPrincipal User loggedUser, Model model)

    {
        User user = userRepo.findByUsername(loggedUser.getUsername());
        System.out.println(user.toString());
        model.addAttribute("user", user);
        return "homepage";
    }

    @PostMapping("/addPost")
    private String addPost(@AuthenticationPrincipal User user, @RequestParam String postContent)
    {
        Post post = new Post(postContent, LocalDate.now());
        user.addPostToUser(post);
        System.out.println(post);
        System.out.println(user.getPosts().size());

        postRepo.save(post);
        userRepo.save(user);
        return "redirect:/mypage";
    }
}

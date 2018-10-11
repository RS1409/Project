package com.app.controller;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String getHome(@AuthenticationPrincipal User loggedUser, Model model)
    {
        User user = userRepo.findByUsername(loggedUser.getUsername());
        System.out.println(user.toString());
        model.addAttribute("user", user);
        return "homepage";
    }
}

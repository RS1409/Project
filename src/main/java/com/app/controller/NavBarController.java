package com.app.controller;

import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NavBarController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/search")
    private String searchUsers(@AuthenticationPrincipal User loggedUser,
                               @RequestParam(required = false) String userName, Model model,
                               @RequestParam(defaultValue = "0") int page) {
        if (loggedUser.getLastSearchRequest() == null) loggedUser.setLastSearchRequest(userName);
        List<User> searchResults = userRepo.findAllByUsernameContaining(loggedUser.getLastSearchRequest(), PageRequest.of(page, 2));

        model.addAttribute("totalPages", searchResults.size());
        model.addAttribute("user", loggedUser);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("content", "search");
        return "homepage";
    }
}

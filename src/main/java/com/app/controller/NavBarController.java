package com.app.controller;

import com.app.dto.UserDTO;
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
        loggedUser.setLastSearchRequest(userName);
        UserDTO userDTO = new UserDTO(loggedUser);
        if (loggedUser.getLastSearchRequest() == null) loggedUser.setLastSearchRequest(userName);

        int size = 4;
        int resultsNumber = userRepo.findByUsernameContaining(loggedUser.getLastSearchRequest()).size();
        int totalPages = resultsNumber % size;
        if (totalPages != 0) {
            if (resultsNumber / size >= 1)
                totalPages = resultsNumber / size + 1;
            else
                totalPages = 1;
        } else
            totalPages = resultsNumber / size;

    List<User> searchResults = userRepo.findAllByUsernameContaining(loggedUser.getLastSearchRequest(), PageRequest.of(page, size));
    
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("user",userDTO);
        model.addAttribute("searchResults",searchResults);
        model.addAttribute("userName",userName);
        model.addAttribute("content","search");
        return"homepage";
}
}

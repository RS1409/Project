package com.app.controller;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.service.ByteConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UserRontroller {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("user", user);
        return "profilePage";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user,
                              @RequestParam String username,
                              @RequestParam String firstname,
                              @RequestParam String lastname,
                              @RequestParam String email,
                              @RequestParam(required = false) String password,
                              @RequestParam("file") MultipartFile file)
    {
        if(username.length() > 0) user.setUsername(username);
        if(firstname.length() > 0) user.setFirstName(firstname);
        if(lastname.length() > 0) user.setLastName(lastname);
        if(email.length() > 0) user.setEmail(email);
        if(password.length() > 0) user.setPassword(encoder.encode(password));

        if(file.getSize() > 0)
        {
            try{
                user.setImg(file.getBytes());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        userRepository.save(user);
        return "redirect:/mypage";
    }
}

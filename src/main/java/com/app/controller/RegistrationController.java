package com.app.controller;


import java.io.ByteArrayOutputStream;

import com.app.Service.ByteConverter;
import com.app.model.Roles;
import com.app.model.User;
import com.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.*;
import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;



    @RequestMapping("/register")
    public String getRegister(Model model)
    {
        model.addAttribute("user",new User());
        return "registration";
    }

    @GetMapping("/reg")
    public String getRegisterForm()
    {
        return "redirect:/register";
    }

    @PostMapping("/addUser")
    public String checkUserInfo(@Valid @ModelAttribute User user,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("file") MultipartFile file)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("Persistance failed");
            return "registration";
        }

        else{
            if(findByName(user) != null)
            {
                model.addAttribute("message", "User with this name already exists");
                return "registration";
            }

            if(findByEmail(user) != null)
            {
                model.addAttribute("message", "This email is already used");
                return "registration";
            }


            try {
                if(file.getSize() != 0) user.setImg(file.getBytes());
                else {
                    File defaultImg = new ClassPathResource("/public/images/defaultProfileImg.jpg").getFile();
                    System.out.println(defaultImg.length());
                    user.setImg(ByteConverter.getBytes(defaultImg));
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            user.setActive(true);
            user.setRoles(Collections.singleton(Roles.USER));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            System.out.println(user);
            repo.save(user);
            System.out.println("User added");
            return "redirect:/";
        }
    }

    public User findByName(User user)
    {
        return repo.findByUsername(user.getUsername());
    }

    public User findByEmail(User user)
    {
        return repo.findByEmail(user.getEmail());
    }


}

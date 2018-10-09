package com.app.controller;


import java.io.ByteArrayOutputStream;
import com.app.model.User;
import com.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository repo;



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
    public String checkUserInfo(@Valid User user,
                                //@RequestParam(required = false) MultipartFile file,
                                BindingResult bindingResult,
                                Model model) {

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

//            try {
//                if(file != null) user.setImg(file.getBytes());
//                else {
//                    File defaultImg = new ClassPathResource("imageHP.jpg").getFile();
//                    user.setImg(getBytes(defaultImg));
//                }
//            }catch (IOException e) {
//                e.printStackTrace();
//            }

            repo.save(user);
            System.out.println("User added");
            return "redirect:/";
        }
    }

    public User findByName(User user)
    {
        return repo.findByUserName(user.getUserName());
    }

    public User findByEmail(User user)
    {
        return repo.findByEmail(user.getEmail());
    }

    public byte[] getBytes(File file) throws FileNotFoundException, IOException
    {
        byte[] buffer = new byte[10_000];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        int read;
        while((read = fis.read(buffer)) != -1) bos.write(buffer, 0, read);
        fis.close();
        bos.close();
        return bos.toByteArray();
    }
}

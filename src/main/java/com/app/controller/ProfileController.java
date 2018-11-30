package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.Preference;
import com.app.model.User;
import com.app.repository.PreferenceRepository;
import com.app.repository.SongRepository;
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
public class ProfileController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PreferenceRepository preferenceRepository;
    @Autowired
    SongRepository songRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private String message;

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user, Model model) {

        //Preference userPreference = preferenceRepository.findByUser(user);
        Preference userPreference = user.getPreference();

        UserDTO userDTO = new UserDTO(user);
        model.addAttribute("user", userDTO);
        model.addAttribute("message", message);
        model.addAttribute("content", "profile");
        model.addAttribute("preference", userPreference);
        message = null;
        return "homepage";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String firstname,
                              @RequestParam(required = false) String lastname,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String password,
                              @RequestParam("file") MultipartFile file,
                              Model model) {
        if (userRepository.findByUsername(username) != null) {
            message = "User with this name already exists";
            System.out.println("User Exists");
            return "redirect:/profile";
        }

        if (userRepository.findByEmail(email) != null) {
            message = "This email is already used";
            System.out.println("User Exists");
            return "redirect:/profile";
        }


        if (username.length() > 0) user.setUsername(username);
        if (firstname.length() > 0) user.setFirstName(firstname);
        if (lastname.length() > 0) user.setLastName(lastname);
        if (email.length() > 0) user.setEmail(email);
        if (password.length() > 0) user.setPassword(encoder.encode(password));

        if (file.getSize() > 0) {
            try {
                user.setImg(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userRepository.save(user);
        return "redirect:/mypage";
    }

    @PostMapping("/profile/preferences")
    public String editPreferences(@AuthenticationPrincipal User user,
                                  @RequestParam(required = false) String title,
                                  @RequestParam(required = false) String songArtist,
                                  @RequestParam(required = false) String artist,
                                  @RequestParam(required = false) String genre,
                                  @ModelAttribute Preference preference) {
        if (artist.length() > 0)
            preference.setFavArtist(artist);
        if (genre.length() > 0)
            preference.setFavGenre(genre);

        if (songRepository.findByArtistAndTitle(songArtist, title) == null) {
            message = "There is no song with this artist name and title in our database. Feel free to add it!";
            return "redirect:/profile";
        } else
            preference.setFavSong(songRepository.findByArtistAndTitle(songArtist, title));

        preferenceRepository.save(preference);
        user.addPreference(preference);
        userRepository.save(user);
        return "redirect:/mypage";
    }

}

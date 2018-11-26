package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.FriendRequest;
import com.app.model.Song;
import com.app.model.User;
import com.app.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class requestController {
    @Autowired
    SongRepository songRepo;

    @GetMapping("/requests")
    public String getRequests(@AuthenticationPrincipal User loggedUser, Model model,
                           @ModelAttribute String content) {

        List<Song> requestList = new ArrayList<>();
        requestList = songRepo.findAllByStatus(Song.Status.REQUESTED);
        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songRequests");
        model.addAttribute("requestList", requestList);
        return "homepage";
    }

    @PostMapping(value="/songRequestAction", params = "Accept")
    public String acceptSong(@RequestParam Long songRequestId, @AuthenticationPrincipal User loggedUser) {
        Song songRequest = songRepo.findById(songRequestId).get();
        songRequest.setStatus(Song.Status.ACCEPTED);
        songRepo.save(songRequest);

        return "redirect:/requests";
    }

    @PostMapping(value="/songRequestAction", params = "Delete")
    public String declineSong(@RequestParam Long songRequestId, @AuthenticationPrincipal User loggedUser) {
        Song songRequest = songRepo.findById(songRequestId).get();
        songRepo.delete(songRequest);
        return "redirect:/requests";
    }
}

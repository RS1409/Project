package com.app.controller;

import com.app.model.Song;
import com.app.model.User;
import com.app.repository.SongRepository;
import com.app.repository.UserRepository;
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
public class songsController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    SongRepository songRepo;

    @GetMapping("/songs")
    public String getSongs(@AuthenticationPrincipal User loggedUser, Model model,
                           @ModelAttribute String content) {

        model.addAttribute("song", new Song());
        model.addAttribute("user", loggedUser);
        model.addAttribute("content", "songForms");
        return "homepage";
    }

    @PostMapping("/addSong")
    public String addSong(@ModelAttribute Song song) {

        if (song.getAlbum().isEmpty())
            song.setAlbum("single");

        if (songRepo.findAll().contains(song)) {
            System.out.println("Song was already added before.");
            return "redirect:/songs";
        }

        songRepo.save(song);
        System.out.println("Song added successfully.");
        return "redirect:/songs";
    }

    @GetMapping("/findSong")
    public String findSong(Model model, @AuthenticationPrincipal User loggedUser,
                           @ModelAttribute String content,
                           @RequestParam String selectedOption,
                           @RequestParam String searchPhrase) {

        List<Song> searchResults = new ArrayList<Song>();

        switch (selectedOption) {
            case "title":
                searchResults = songRepo.findByTitleContaining(searchPhrase);
                break;
            case "artist":
                searchResults = songRepo.findByArtistContaining(searchPhrase);
                break;
            case "album":
                searchResults = songRepo.findByAlbumContaining(searchPhrase);
                break;
            case "genre":
                searchResults = songRepo.findAllByGenre(searchPhrase);
                break;
        }


        model.addAttribute("song", new Song());
        model.addAttribute("user", loggedUser);

        model.addAttribute("songSearch", searchResults);
        model.addAttribute("content", "songList");

        return "homepage";
    }
}

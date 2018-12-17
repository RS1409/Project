package com.app.controller;

import com.app.dto.UserDTO;
import com.app.model.Preference;
import com.app.model.Roles;
import com.app.model.Song;
import com.app.model.User;
import com.app.repository.PreferenceRepository;
import com.app.repository.SongRepository;
import com.app.repository.UserRepository;
import com.app.service.GenresGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class SongController {
    @Autowired
    SongRepository songRepository;
    @Autowired
    PreferenceRepository preferenceRepository;
    @Autowired
    UserRepository userRepository;

    private List<Song> searchResults = new ArrayList<>();
    private int totalPages = 0;
    private String selectedOptionGlobal, searchPhraseGlobal;

    @GetMapping("/songs")
    public String getSongs(@AuthenticationPrincipal User loggedUser, Model model,
                           @ModelAttribute String content) {

        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songs");
        return "homepage";
    }

    @GetMapping("/addForm")
    public String addForm(@AuthenticationPrincipal User loggedUser, Model model,
                          @ModelAttribute String content) {


        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songAdd");
        model.addAttribute("song", new Song());
        model.addAttribute("genres", GenresGenerator.addGenres());
        System.out.print(GenresGenerator.addGenres());
        return "homepage";
    }

    @PostMapping("/addSong")
    public String addSong(@AuthenticationPrincipal User loggedUser, @ModelAttribute Song song) {
        if (song.getAlbum().isEmpty())
            song.setAlbum("single");

        if (songRepository.findAll().contains(song)) {
            System.out.println("Song was already added before.");
            return "redirect:/songs";
        }

        if (loggedUser.getRoles().contains(Roles.ROLE_ADMIN)){
            song.setStatus(Song.Status.ACCEPTED);
        }
            songRepository.save(song);
        System.out.println("Song added successfully.");
        return "redirect:/songs";
    }

    @GetMapping("/findSong")
    public String findSong(Model model, @AuthenticationPrincipal User loggedUser,
                           @ModelAttribute String content,
                           @RequestParam String selectedOption,
                           @RequestParam String searchPhrase,
                           @RequestParam(defaultValue = "0") int page) {
        int size = 20;
        int resultsNumber = 0;
        switch (selectedOption) {
            case "title":
                resultsNumber = songRepository.findAllByStatusAndTitleContaining(Song.Status.ACCEPTED, searchPhrase).size();
                break;
            case "artist":
                resultsNumber = songRepository.findAllByStatusAndArtistContaining(Song.Status.ACCEPTED, searchPhrase).size();
                break;
            case "album":
                resultsNumber = songRepository.findAllByStatusAndAlbumContaining(Song.Status.ACCEPTED, searchPhrase).size();
                break;
            case "genre":
                resultsNumber = songRepository.findAllByStatusAndGenre(Song.Status.ACCEPTED, searchPhrase).size();
                break;
        }

        totalPages = resultsNumber % size;
        if (totalPages != 0) {
            if (resultsNumber / size >= 1)
                totalPages = resultsNumber / size + 1;
            else
                totalPages = 1;
        } else
            totalPages = resultsNumber / size;


        switch (selectedOption) {
            case "title":
                searchResults = songRepository.findByStatusAndTitleContaining(Song.Status.ACCEPTED, searchPhrase, PageRequest.of(page, size));
                break;
            case "artist":
                searchResults = songRepository.findByStatusAndArtistContaining(Song.Status.ACCEPTED, searchPhrase, PageRequest.of(page, size));
                break;
            case "album":
                searchResults = songRepository.findByStatusAndAlbumContaining(Song.Status.ACCEPTED, searchPhrase, PageRequest.of(page, size));
                break;
            case "genre":
                searchResults = songRepository.findByStatusAndGenre(Song.Status.ACCEPTED, searchPhrase, PageRequest.of(page, size));
                break;
        }

        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songList");
        model.addAttribute("selectedOption", selectedOption);
        selectedOptionGlobal = selectedOption;
        model.addAttribute("searchPhrase", searchPhrase);
        searchPhraseGlobal = searchPhrase;
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchResults", searchResults);
        return "homepage";
    }

    @GetMapping("/songPlay")
    public String songPlay(@AuthenticationPrincipal User loggedUser, Model model,
                           @ModelAttribute String content,
                           @RequestParam String link) {

        String correctLink = "https://www.youtube.com/embed/" + link.substring(32, 43);

        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songPlay");
        model.addAttribute("link", correctLink);
        model.addAttribute("selectedOption", selectedOptionGlobal);
        model.addAttribute("searchPhrase", searchPhraseGlobal);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchResults", searchResults);
        return "homepage";
    }

    @PostMapping("/songFavourite")
    public String setFavouriteSong(@AuthenticationPrincipal User loggedUser, Model model, @RequestParam Long songId) {
        Preference preference = loggedUser.getPreference();
        preference.setFavSong(songRepository.getOne(songId));
        preferenceRepository.save(preference);
        loggedUser.addPreference(preference);
        userRepository.save(loggedUser);


        UserDTO userDTO = new UserDTO(loggedUser);
        model.addAttribute("user", userDTO);
        model.addAttribute("content", "songList");
        model.addAttribute("selectedOption", selectedOptionGlobal);
        model.addAttribute("searchPhrase", searchPhraseGlobal);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchResults", searchResults);
        return "homepage";
    }
}

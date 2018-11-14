package com.app.controller;

import com.app.model.Comment;
import com.app.model.Post;
import com.app.model.User;
import com.app.repository.CommentRepository;
import com.app.repository.PostRepository;
import com.app.repository.UserRepository;
import com.app.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentRepository commentRepo;
//
//    @GetMapping("/log.css")
//    public String redirectCss()
//    {
//        return "redirect:/mypage";
//    }

    @GetMapping("/")
    public String redirectHome()
    {
        return "redirect:/mypage";
    }

    @GetMapping("/mypage")
    public String getHome(@AuthenticationPrincipal User loggedUser, Model model)
    {
        User user = userRepo.findByUsername(loggedUser.getUsername());
        System.out.println(user.toString());
        model.addAttribute("user", user);
        model.addAttribute("content", "posts");
        return "homepage";
    }

    @PostMapping("/addPost")
    private String addPost(@AuthenticationPrincipal User user,
                           @RequestParam String postContent)
    {
        if(postContent.length() != 0)
        {
            Post post = new Post(postContent, DateTimeService.getCurrentTime());
            user.addPostToUser(post);
            System.out.println(post);
            System.out.println(user.getPosts().size());

            postRepo.save(post);
            userRepo.save(user);
            return "redirect:/mypage";
        } else return "redirect:/mypage";
    }

    @PostMapping("/commentPost")
    private String addComment(@RequestParam Long postId,
                              @RequestParam String commentContent,
                              @AuthenticationPrincipal User user)
    {
        if(commentContent.length() != 0)
        {
            Comment comment = new Comment(commentContent, DateTimeService.getCurrentTime(), user.getUsername());
            Post post = postRepo.getOne(postId);
            post.addCommentToPost(comment);

            commentRepo.save(comment);
            postRepo.save(post);
            return "redirect:/mypage";
        } else return "redirect:/mypage";
    }

    @GetMapping("/search")
    private String searchUsers(@AuthenticationPrincipal User loggedUser,
                               @RequestParam(required = false) String userName,  Model model,
                               @RequestParam(defaultValue="0") int page)
    {
        if(loggedUser.getLastSearchRequest() == null) loggedUser.setLastSearchRequest(userName);
        List<User> searchResults = userRepo.findAllByUsernameContaining(loggedUser.getLastSearchRequest(), PageRequest.of(page, 2));

        model.addAttribute("totalPages",searchResults.size());
        model.addAttribute("user", loggedUser);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("content", "search");
        return "homepage";
    }


}

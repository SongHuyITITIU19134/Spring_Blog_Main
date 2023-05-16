package com.example.springbootblog.controllers;

import com.example.springbootblog.models.Account;
import com.example.springbootblog.models.Comment;
import com.example.springbootblog.models.Post;
import com.example.springbootblog.service.AccountService;
import com.example.springbootblog.service.CommentService;
import com.example.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class MyProfile {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/person/{email}")
    public String getAccount(@PathVariable String email, Model model){
        //find the post by id
        // Using Optional because it will check the value in post and load value if post has
        // If they dont find out the value of post, they will return null or display the exception
        Optional<Account> optionalAccount=accountService.findOneByEmail(email);
        // Check the Object Post was existed, if true, load the object post by method GET
        // and add them to the model Spring MVC by method addAttribute() of Model
        if(optionalAccount.isPresent()){
          Account account = optionalAccount.get();
            model.addAttribute("account", account);
            return "MyProfile";
        }else {
            return "404";
        }
    }
}

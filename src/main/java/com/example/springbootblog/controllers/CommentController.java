package com.example.springbootblog.controllers;

import com.example.springbootblog.models.Comment;
import com.example.springbootblog.models.Post;
import com.example.springbootblog.service.CommentService;
import com.example.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Controller

public class CommentController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    // GET Mapping to display the comment form page
    @GetMapping("/cmt/{id}/new")
    @PreAuthorize("isAuthenticated()")
    public String showCommentForm(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        // if post exit put it in model
        if (optionalPost.isPresent()) {
            Post post = commentService.getPostById(id);
            Comment comment = new Comment();
            comment.setPost(post);
            model.addAttribute("post", post);
            model.addAttribute("comment", comment);
            return "post_comment";
        } else {
            return "404";

        }
    }

    @PostMapping("/cmt/{id}")
    @PreAuthorize("isAuthenticated()")
    public String submitComment(@ModelAttribute Comment comment) {
        commentService.saveComment(comment);
        return "redirect:/posts/" + comment.getPost().getId();
    }


    @GetMapping("/cmt/{id}/editCmt")
    @PreAuthorize("isAuthenticated()")
    public String getCommentForm(@PathVariable Long id, Model model) {
      Optional<Comment> optionalComment = commentService.getById(id);
        // if post exit put it in model
        if ( optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            model.addAttribute("comment", comment);
            return "Update_Cmt";
        }
        return "404";
    }

    @PostMapping("/cmt/{id}/editCmt")
    @PreAuthorize("isAuthenticated()")
    public String updateCMT(@PathVariable Long id,Comment comment, BindingResult result, Model model){

        Optional<Comment> optionalComment = commentService.getById(id);
        if(optionalComment.isPresent()){
            Comment existingCMT = optionalComment.get();
            existingCMT.setContent(comment.getContent());
           commentService.saveComment( existingCMT);

        }
        return "redirect:/posts/" + comment.getPost().getId();

    }

    @GetMapping("/cmt/{id}/delete")
    public String deleteCMT(@PathVariable Long id){
        //find cmt by id
        Optional<Comment> optionalComment = commentService.getById(id);
        if(optionalComment.isPresent()){
         Comment comment = optionalComment.get();
            commentService.delete(comment);
            return  "redirect:/";
        } else {
            return "404";
        }
    }
}











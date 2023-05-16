package com.example.springbootblog.controllers;

import com.example.springbootblog.models.Account;
import com.example.springbootblog.models.Comment;
import com.example.springbootblog.models.Post;
import com.example.springbootblog.repositories.PostRepository;
import com.example.springbootblog.service.AccountService;
import com.example.springbootblog.service.CommentService;
import com.example.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/person/{id}")
    public String getPost(@PathVariable Long id, Model model){
        //find the post by id
        // Using Optional because it will check the value in post and load value if post has
        // If they dont find out the value of post, they will return null or display the exception
      Optional<Post> optionalPost=postService.getById(id);
        // Check the Object Post was existed, if true, load the object post by method GET
        // and add them to the model Spring MVC by method addAttribute() of Model
        if(optionalPost.isPresent()){
            List<Comment> comments = commentService.getCommentsByPost(optionalPost.get());
            Post post =optionalPost.get();
            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
            return "post";
        }else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model){
        Optional<Account> optionalAccount = accountService.findOneByEmail("user1@gmail.com");
        if(optionalAccount.isPresent()){
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post",post);
            return "post_new";

        }else{
            return "404";
        }
    }
    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts/" +post.getId();

    }

    @GetMapping("/posts/{id}/edit")
   @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        // if post exit put it in model
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            return "post_edit";
        }else{
            return "404";
        }

    }
    @PostMapping("/posts/{id}")
 @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post, BindingResult result, Model model){

        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            postService.save(existingPost);

        }
        return "redirect:/posts/" + post.getId();

    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){
        //find post by id
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            List<Comment> comments = commentService.getCommentsByPost(optionalPost.get());
            comments.forEach(comment -> commentService.delete(comment));
            postService.delete(post);
            return  "redirect:/";
        } else {
            return "404";
        }
    }





}

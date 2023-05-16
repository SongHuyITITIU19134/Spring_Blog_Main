package com.example.springbootblog.config;

import com.example.springbootblog.models.Account;
import com.example.springbootblog.models.Authority;
import com.example.springbootblog.models.Comment;
import com.example.springbootblog.repositories.AuthorityRepository;
import com.example.springbootblog.service.AccountService;
import com.example.springbootblog.service.CommentService;
import com.example.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.springbootblog.models.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private CommentService commentService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if(posts.size()==0){

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);



            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstname("user1");
            account1.setLastname("user1");
            account1.setEmail("user1@gmail.com");
            account1.setPassword("user");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);

            account2.setFirstname("admin");
            account2.setLastname("admin");
            account2.setEmail("admin@gmail.com");
            account2.setPassword("admin");
            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);


            accountService.save(account1);
            accountService.save(account2);


            Post post1 = new Post();
            post1.setTitle("Title of post 1 ");
            post1.setBody("Lorem ipsum dolor sit amet consectetur adipisicing elit. Id totam voluptatem saepe eius ipsum nam provident sapiente, natus et vel eligendi laboriosam odit eos temporibus impedit veritatis ut, illo deserunt illum voluptate quis beatae quod. Necessitatibus provident dicta consectetur labore! ");
            post1.setAccount(account1);


            Post post2 = new Post();
            post2.setTitle("Title of post 2 ");
            post2.setBody("Lorem ipsum dolor sit amet consectetur adipisicing elit. Id totam voluptatem saepe eius ipsum nam provident sapiente, natus et vel eligendi laboriosam odit eos temporibus impedit veritatis ut, illo deserunt illum voluptate quis beatae quod. Necessitatibus provident dicta consectetur labore! ");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);

            Comment comment1 = new Comment();
            comment1.setPost(post1);
            comment1.setContent("Hay qua");
            commentService.saveComment(comment1);



        }

}
    }


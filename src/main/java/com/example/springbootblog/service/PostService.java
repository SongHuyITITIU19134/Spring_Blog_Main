package com.example.springbootblog.service;

import com.example.springbootblog.models.Comment;
import com.example.springbootblog.models.Post;
import com.example.springbootblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Post save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdateAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    public void delete(Post post){
        postRepository.delete(post);

    }


    public Optional<Post> getPostById(Long id) {
         return postRepository.findById(id);
    }
}

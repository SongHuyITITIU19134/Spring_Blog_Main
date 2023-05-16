package com.example.springbootblog.service;

import com.example.springbootblog.models.Comment;
import com.example.springbootblog.models.Post;
import com.example.springbootblog.repositories.CommentRepository;
import com.example.springbootblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }




    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPostId(post.getId());
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }


    public Optional<Comment> getById(Long id) {
       return commentRepository.findById(id);
    }
}



package com.example.springbootblog.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // Using this annotation to configure the increasing column that was determined

    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id", nullable = false)
    private Account account;


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", comments=" + comments +
                ", account=" + account +
                '}';
    }
}

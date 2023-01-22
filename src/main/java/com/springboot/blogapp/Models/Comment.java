package com.springboot.blogapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment{
    // Comment Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user who put the comment
    @Column(name="username")
    private String userName;

    // Content of the comment
    @Column(name="content")
    private String content;

    // emailId of user who put the comment
    @Column(name="email_id")
    private String emailId;

    // PostId for which comment was made
    @Column(name="post_id")
    private Long postId;
}
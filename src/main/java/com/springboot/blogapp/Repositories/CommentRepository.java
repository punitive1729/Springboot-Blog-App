package com.springboot.blogapp.Repositories;

import com.springboot.blogapp.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find all Comments where postId=postId
    List<Comment> findByPostId(Long postId);
}

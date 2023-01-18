package com.springboot.blogapp.Services;

import com.springboot.blogapp.DTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getAPostById(Long postId);

    void deletePostById(Long postId);

    PostDTO updateAPostById(Long postId, PostDTO postDTO);
}

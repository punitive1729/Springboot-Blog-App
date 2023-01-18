package com.springboot.blogapp.Services.Impl;

import com.springboot.blogapp.DTO.PostDTO;
import com.springboot.blogapp.Exceptions.ResourceNotFoundException;
import com.springboot.blogapp.Models.Post;
import com.springboot.blogapp.Repositories.PostRepository;
import com.springboot.blogapp.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private Post mapPostDTOtoPost(PostDTO postDTO){
        Post post=new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        return post;
    }

    private PostDTO mapPostToPostDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setDescription(post.getDescription());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // Convert PostDTO -> Post
        Post post=mapPostDTOtoPost(postDTO);

        // Save the post to DB
        Post savedPost=postRepository.save(post);

        // Return PostDTO Response
        return mapPostToPostDTO(savedPost);
    }

    private Post getAPost(Long postId){
        return postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","postId",Long.toString(postId)));
    }

    @Override
    public List<PostDTO> getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort=Sort.by(sortBy).ascending();
        if(sortDir.equalsIgnoreCase("DESC"))
            sort = Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);

        // Get all posts
        Page<Post> pageOfPosts=postRepository.findAll(pageable);

        List<Post> listOfPosts=pageOfPosts.getContent();

        return listOfPosts.stream().map(post->mapPostToPostDTO(post))
                .collect(Collectors.toList());

    }

    @Override
    public PostDTO getAPostById(Long postId) {
        return mapPostToPostDTO(getAPost(postId));
    }

    @Override
    public void deletePostById(Long postId) {
        // Check if there is a post with given postId
        Post post=getAPost(postId);

        // Delete the post
        postRepository.delete(post);
    }

    private Post updatePost(Long postId,PostDTO postDTO){
        // Get the post with postId
        Post post=getAPost(postId);

        // Make the necessary updates
        post.setDescription(postDTO.getDescription());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        return post;
    }

    @Override
    public PostDTO updateAPostById(Long postId, PostDTO postDTO) {
        Post post=updatePost(postId,postDTO);

        // Update the post
        Post updatedPost=postRepository.save(post);

        // Send back the updated Post as response
        return mapPostToPostDTO(updatedPost);
    }

}

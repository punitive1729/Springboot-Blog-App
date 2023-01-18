package com.springboot.blogapp.Controllers;

import com.springboot.blogapp.DTO.PostDTO;
import com.springboot.blogapp.Models.Post;
import com.springboot.blogapp.Services.PostService;
import com.springboot.blogapp.utils.AppContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/health-check")
    public ResponseEntity<String> checkApi(){
        /*
        *  URL: http://localhost:8080/api/posts/health-check
        *  METHOD: GET
        * */
        return ResponseEntity.ok("POSTs API Working...");
    }
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        /*
        *
        *  URL: http://localhost:8080/api/posts
        *  HTTP-METHOD: POST
        *
        */
        System.out.println("Creating Post..");
        PostDTO postDTOResponse=postService.createPost(postDTO);
        return new ResponseEntity<>(postDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(name="pageNo", defaultValue = AppContants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(name="pageSize", defaultValue = AppContants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name="sortBy", defaultValue = AppContants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name="sortDir", defaultValue = AppContants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        /*
         *
         *  URL: http://localhost:8080/api/posts
         *  HTTP-METHOD: GET
         *
         */
        System.out.println("Getting all Posts..");
        List<PostDTO> listOfPostDTOs=postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(listOfPostDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getAPostById(@PathVariable(name = "id") Long postId){
        /*
         *
         *  URL: http://localhost:8080/api/posts/id
         *  HTTP-METHOD: GET
         *
         */
        System.out.println("Get a post by Id: "+postId);
        return ResponseEntity.ok(postService.getAPostById(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAPostById(@PathVariable(name = "id") Long postId){
        /*
         *
         *  URL: http://localhost:8080/api/posts/id
         *  HTTP-METHOD: DELETE
         *
         */
        System.out.println("Deleting a post by Id: "+postId);
        postService.deletePostById(postId);
        return ResponseEntity.ok(String.format("Post with postId: %s deleted successfully.", postId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> updateAPostById(@PathVariable(name = "id") Long postId,
                                                @RequestBody PostDTO postDTO){
        /*
         *
         *  URL: http://localhost:8080/api/posts/id
         *  HTTP-METHOD: PATCH
         *
         */
        System.out.println("Updating a post by Id: "+postId);
        PostDTO updatedPost=postService.updateAPostById(postId,postDTO);
        return ResponseEntity.ok(updatedPost);
    }
}


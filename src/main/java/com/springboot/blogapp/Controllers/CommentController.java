package com.springboot.blogapp.Controllers;

import com.springboot.blogapp.DTO.CommentDTO;
import com.springboot.blogapp.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name="postId") Long postId,
                                                    @RequestBody CommentDTO commentDTO){
        /*
         *  URL: http://localhost:8080/api/posts/{postId}/comments
         *  METHOD: POST
         * */
        System.out.println("Creating comment on a Post with PostId: "+postId);
        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForAPost(@PathVariable(name = "postId") Long postId){
        /*
         *  URL: http://localhost:8080/api/posts/{postId}/comments
         *  METHOD: POST
         * */
        System.out.println("Getting all comments for a Post with PostId: "+postId);
        return ResponseEntity.ok(commentService.getAllCommentsForAPost(postId));

    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getACommentForAPost(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name="commentId") Long commentId){
        /*
         *  URL: http://localhost:8080/api/posts/{postId}/comments/{commentId}
         *  METHOD: GET
         * */
        System.out.println("Getting all comments for a Post with PostId: "+postId);
        return ResponseEntity.ok(commentService.getACommentForAPost(postId,commentId));

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteACommentOnAPost(@PathVariable(name="postId") Long postId,
                                                        @PathVariable(name="commentId") Long commentId){
        /*
         *  URL: http://localhost:8080/api/posts/{postId}/comments/{commentId}
         *  METHOD: DELETE
         * */
        System.out.println("Deleting comment on a Post");
        commentService.deleteACommentForAPost(postId,commentId);
        return ResponseEntity.ok("Comment deleted successfully.");
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateACommentOnAPost(@PathVariable(name="postId") Long postId,
                                                        @PathVariable(name="commentId") Long commentId,
                                                        @RequestBody CommentDTO commentDTO){
        /*
         *  URL: http://localhost:8080/api/posts/{postId}/comments/{commentId}
         *  METHOD: DELETE
         * */
        System.out.println("Deleting comment on a Post");
        return ResponseEntity.ok(commentService.updateACommentOnAPost(postId,commentId,commentDTO));
    }

}

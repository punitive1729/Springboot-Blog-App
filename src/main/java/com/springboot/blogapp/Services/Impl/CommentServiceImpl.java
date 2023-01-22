package com.springboot.blogapp.Services.Impl;

import com.springboot.blogapp.DTO.CommentDTO;
import com.springboot.blogapp.Exceptions.BlogAPIException;
import com.springboot.blogapp.Exceptions.ResourceNotFoundException;
import com.springboot.blogapp.Models.Comment;
import com.springboot.blogapp.Models.Post;
import com.springboot.blogapp.Repositories.CommentRepository;
import com.springboot.blogapp.Repositories.PostRepository;
import com.springboot.blogapp.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private CommentDTO mapCommentToCommentDTO(Comment comment){
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setContent(comment.getContent());
        commentDTO.setEmailId(comment.getEmailId());
        commentDTO.setUserName(comment.getUserName());
        return commentDTO;
    }


    private Comment mapCommentDTOToComment(Long postId, CommentDTO commentDTO){
        Comment comment=new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setEmailId(commentDTO.getEmailId());
        comment.setUserName(commentDTO.getUserName());
        comment.setPostId(postId);
        return comment;
    }

    private Post checkIfPostExists(Long postId){
        // Check if a post with the particular postId exists
        return postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","PostId",String.valueOf(postId)));
    }

    private Comment checkIfCommentExists(Long commentId, Long postId){
        // Check if a post with the particular postId exists
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","CommentId",String.valueOf(commentId)));

        // If comment does not belong to the required post
        if(!comment.getPostId().equals(postId))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"No such comment for the post");

        return comment;
    }
    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

        // Check if Post Exists
        Post post=checkIfPostExists(postId);

        System.out.println("Post received:\n"+ postId);
        // Get comment from DTO class
        Comment comment=mapCommentDTOToComment(postId, commentDTO);

        // Save the comment in DB
        comment=commentRepository.save(comment);

        // Return comment DTO back to user as response
        return mapCommentToCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentsForAPost(Long postId) {
        // Check if Post Exists
        Post post=checkIfPostExists(postId);

        // Get all comments belonging to that Post
        List<Comment> listOfComments=commentRepository.findByPostId(postId);

        // Return the list Of all comments belonging to that post
        return listOfComments.stream().map(comment -> mapCommentToCommentDTO(comment)).toList();
    }

    @Override
    public CommentDTO getACommentForAPost(Long postId, Long commentId) {
        // Check if Post exists
        Post post=checkIfPostExists(postId);

        // Get the comment with particular Id and for the required post
        Comment comment=checkIfCommentExists(commentId, postId);

        return mapCommentToCommentDTO(comment);
    }

    @Override
    public void deleteACommentForAPost(Long postId, Long commentId) {
        // Check if Post exists
        Post post=checkIfPostExists(postId);

        // Get the comment with particular Id
        Comment comment=checkIfCommentExists(commentId, postId);

        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateACommentOnAPost(Long postId, Long commentId, CommentDTO commentDTO) {
        // Check if Post exists
        Post post=checkIfPostExists(postId);

        // Get the comment with particular Id
        Comment comment=checkIfCommentExists(commentId, postId);

        // The only thing that user is allowed to update is the content of the comment
        comment.setContent(commentDTO.getContent());

        // Updated Comment
        Comment updatedComment=commentRepository.save(comment);

        return mapCommentToCommentDTO(updatedComment);
    }

}

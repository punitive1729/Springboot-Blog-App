package com.springboot.blogapp.Services;

import com.springboot.blogapp.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsForAPost(Long postId);

    CommentDTO getACommentForAPost(Long postId,Long commentId);

    void deleteACommentForAPost(Long postId,Long commentId);

    CommentDTO updateACommentOnAPost(Long postId,Long commentId,CommentDTO commentDTO);
}

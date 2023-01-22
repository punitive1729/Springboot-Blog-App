package com.springboot.blogapp.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private String content;
    private String userName;
    private String emailId;
}

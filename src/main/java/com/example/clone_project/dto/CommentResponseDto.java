package com.example.clone_project.dto;

import com.example.clone_project.entity.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    public Long id;
    public String content;
    public String author;
    public LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getMember().getUsername();
        this.modifiedAt = comment.getModifiedAt();
    }
}

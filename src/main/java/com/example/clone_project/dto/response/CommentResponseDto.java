package com.example.clone_project.dto.response;

import com.example.clone_project.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    public Long id;
    public String content;
    public String author;
    public String authorNickname;
    public LocalDateTime modifiedAt;
    public String profileImage;

    public CommentResponseDto(Long id, String content, String author,String authorNickname ,LocalDateTime modifiedAt, String profileImage) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.authorNickname = authorNickname;
        this.modifiedAt = modifiedAt;
        this.profileImage = profileImage;
    }

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent(),comment.getMember().getUsername() ,comment.getMember().getNickname(), comment.getModifiedAt(), comment.getMember().getProfileImageUrl());
    }
}

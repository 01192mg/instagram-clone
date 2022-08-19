package com.example.clone_project.dto;

import com.example.clone_project.entity.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String content;
    private final String author;
    private final List<CommentResponseDto> comments;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.author = post.getMember().getUsername();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.modifiedAt = post.getModifiedAt();
    }
}

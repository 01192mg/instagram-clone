package com.example.clone_project.dto.response;

import com.example.clone_project.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String content;
    private final String author;
    private final List<CommentResponseDto> comments;

    private final String imageUrl;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.author = post.getMember().getUsername();
        this.comments = Optional.ofNullable(post.getComments()).orElseGet(Collections::emptyList).stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.imageUrl = post.getImageUrl();
        this.modifiedAt = post.getModifiedAt();
    }
}

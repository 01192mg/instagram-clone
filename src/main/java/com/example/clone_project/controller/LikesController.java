package com.example.clone_project.controller;

import com.example.clone_project.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/api/posts/like/{postId}}") // 게시글 좋아요
    public void likesToPost(@PathVariable Long postId){
        likesService.likesToPost(postId);
    }

}

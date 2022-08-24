package com.example.clone_project.controller;

import com.example.clone_project.dto.request.PostRequestDto;
import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.security.MemberDetailsImpl;
import com.example.clone_project.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseDto<?> createPosts(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.creatPosts(memberDetails.getMember(), postRequestDto.getContent(),postRequestDto.getFile()));
    }

    //    특정 게시물 검색
    @GetMapping("/api/posts/{postId}")
    public ResponseDto<?> getPosts(@PathVariable Long postId) {
        return ResponseDto.success(postService.getPosts(postId));
    }

    @GetMapping("/api/posts")
    public ResponseDto<?> getAllPosts() {
        return ResponseDto.success(postService.getAllPosts());
    }

    @GetMapping("/api/posts/search")
    public ResponseDto<?> searchPosts(@RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.searchPosts(postRequestDto));
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseDto<?> updatePosts(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.updatePosts(memberDetails.getMember(), postId, postRequestDto));
    }

    @DeleteMapping("/api/posts/{postId}")
    public  ResponseDto<?> deletePosts(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long postId){
        postService.deletePosts(memberDetails.getMember(), postId);
        return ResponseDto.success(null);
    }
}

package com.example.clone_project.controller;

import com.example.clone_project.dto.request.PostRequestDto;
import com.example.clone_project.dto.response.PostResponseDto;
import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.entity.Post;
import com.example.clone_project.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseDto<?> createPosts(@RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.creatPosts(postRequestDto.getContent(),postRequestDto.getFile()));
    }

    //    특정 게시물 검색
    @GetMapping("/api/posts/{postId}")
    public ResponseDto<Post> getPosts(@PathVariable Long postId) {
        return ResponseDto.success(postService.getPosts(postId));
    }

    @GetMapping("/api/posts")
    public ResponseDto<List<Post>> getAllPosts() {
        return ResponseDto.success(postService.getAllPosts());
    }

    @GetMapping("/api/posts/search")
    public ResponseDto<List<Post>> searchPosts(@RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.searchPosts(postRequestDto));
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseDto<PostResponseDto> updatePosts(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        return ResponseDto.success(postService.updatePosts(postId, postRequestDto));
    }

    @DeleteMapping("/api/posts/{postId}")
    public  ResponseDto<PostResponseDto> deletePosts(@PathVariable Long postId){
        postService.deletePosts(postId);
        return ResponseDto.success(null);
    }
}

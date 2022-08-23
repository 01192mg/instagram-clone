package com.example.clone_project.service;

import com.example.clone_project.dto.request.PostRequestDto;
import com.example.clone_project.dto.response.PostResponseDto;
import com.example.clone_project.entity.Post;
import com.example.clone_project.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post creatPosts(String content, String file ) {
        Post post = Post.builder().content(content).imageUrl(file).build();
        return postRepository.save(post);

    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts;
    }

    public Post getPosts(Long postId){
        Post post = postRepository.findAllById(postId);
        return post;
    }

    public List<Post> searchPosts(PostRequestDto content){
        List<Post> posts = postRepository.findAllByContentContaining(content.getContent());
        return posts;
    }


    @Transactional
    public PostResponseDto updatePosts(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.update(postRequestDto);
        return new PostResponseDto(postRepository.save(post));
    }
    @Transactional
    public void deletePosts(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        postRepository.delete(post);
    }
}

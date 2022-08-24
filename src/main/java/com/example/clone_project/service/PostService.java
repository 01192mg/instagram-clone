package com.example.clone_project.service;

import com.example.clone_project.dto.request.PostRequestDto;
import com.example.clone_project.dto.response.PostResponseDto;
import com.example.clone_project.entity.Member;
import com.example.clone_project.entity.Post;
import com.example.clone_project.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto creatPosts(Member member , String content, String file ) {
        Post post = Post.builder().member(member).content(content).imageUrl(file).build();
        Post postsaved = postRepository.save(post);
        return new PostResponseDto(postsaved);

    }

    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    public PostResponseDto getPosts(Long postId){
        Post post = postRepository.findAllById(postId);
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> searchPosts(PostRequestDto content){
        List<Post> posts = postRepository.findAllByContentContaining(content.getContent());
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }


    @Transactional
    public PostResponseDto updatePosts(Member member, Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(!member.getUsername().equals(post.getMember().getUsername())) {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }
    @Transactional
    public void deletePosts(Member member, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(!member.getUsername().equals(post.getMember().getUsername())){
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}

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
    public PostResponseDto updatePosts(Member member, Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(!member.equals(post.getMember())) {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
        post.update(postRequestDto);
        return new PostResponseDto(postRepository.save(post));
    }
    @Transactional
    public void deletePosts(Member member, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(!member.equals(post.getMember())){
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}

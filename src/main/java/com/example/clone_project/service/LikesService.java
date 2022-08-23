package com.example.clone_project.service;

import com.example.clone_project.entity.likes.Likes;
import com.example.clone_project.entity.likes.LikesRepository;
import com.example.clone_project.entity.member.Member;
import com.example.clone_project.entity.member.MemberRepository;
import com.example.clone_project.entity.post.Post;
import com.example.clone_project.entity.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void likesToPost(Long postId){ // 게시글에 좋아요
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if(!likesRepository.existsByMemberAndPost(member,post)){
            likesRepository.save(Likes.builder()
                    .member(member)
                    .post(post)
                    .build());
        } else{
            likesRepository.deleteByMemberAndPost(member, post);
        }
    }
}

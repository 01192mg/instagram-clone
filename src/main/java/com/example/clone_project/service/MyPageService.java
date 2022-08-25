package com.example.clone_project.service;

import com.example.clone_project.dto.request.ProfileUpdateRequestDto;
import com.example.clone_project.dto.response.MemberResponseDto;
import com.example.clone_project.dto.response.PostResponseDto;
import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.entity.Member;
import com.example.clone_project.repository.MemberRepository;
import com.example.clone_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MyPageService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public ResponseDto<?> myPosts(UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<PostResponseDto> myPosts =
                postRepository.findByUsername(username).stream().map(PostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.success(myPosts);
    }

    public ResponseDto<?> updateProfile(ProfileUpdateRequestDto updateDto, UserDetails userDetails) {
        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updateProfile(updateDto.getNickname(), updateDto.getProfileImage());
        return ResponseDto.success(new MemberResponseDto(member));
    }
}

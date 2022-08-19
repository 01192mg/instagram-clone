package com.example.clone_project.service;

import com.example.clone_project.dto.MemberResponseDto;
import com.example.clone_project.dto.PostResponseDto;
import com.example.clone_project.dto.ProfileUpdateRequestDto;
import com.example.clone_project.dto.ResponseDto;
import com.example.clone_project.entity.member.Member;
import com.example.clone_project.entity.member.MemberRepository;
import com.example.clone_project.entity.post.PostRepository;
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
    private final S3UploadService uploadService;

    public ResponseDto<?> myPosts(UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<PostResponseDto> myPosts =
                postRepository.findByUsername(username).stream().map(PostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.success(myPosts);
    }

    public ResponseDto<?> updateProfile(ProfileUpdateRequestDto updateDto, UserDetails userDetails) {
        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        String imageUrl = uploadService.uploadImage(updateDto.getProfileImage());
        member.updateProfile(updateDto.getNickname(), imageUrl);
        return ResponseDto.success(new MemberResponseDto(member));
    }
}

package com.example.clone_project.service;

import com.example.clone_project.dto.response.PostResponseDto;
import com.example.clone_project.dto.response.ResponseDto;
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

    public ResponseDto<?> myPosts(UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<PostResponseDto> myPosts =
                postRepository.findByUsername(username).stream().map(PostResponseDto::new).collect(Collectors.toList());
        return ResponseDto.success(myPosts);
    }
}

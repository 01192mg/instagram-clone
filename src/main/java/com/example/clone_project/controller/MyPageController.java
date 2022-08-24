package com.example.clone_project.controller;

import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/mypage/posts")
    public ResponseDto<?> myPosts(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.myPosts(userDetails);
    }

}

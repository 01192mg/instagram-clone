package com.example.clone_project.controller;

import com.example.clone_project.dto.request.LoginRequestDto;
import com.example.clone_project.dto.request.MemberRequestDto;
import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    //Valid는 유효성 검사
    @PostMapping(value = "/api/members/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @PostMapping(value = "/api/members/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

    // 아이디 중복 체크
    @GetMapping("/api/members/check/{username}")
    public ResponseDto<?> checkId(@PathVariable String username) {
        return memberService.checkId(username);
    }

    //회원 정보 조회
    @GetMapping("/api/members/info")
    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetails userInfo) {
        return memberService.LoginInfo(userInfo);
    }
}

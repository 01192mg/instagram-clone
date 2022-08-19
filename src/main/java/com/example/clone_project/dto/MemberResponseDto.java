package com.example.clone_project.dto;

import com.example.clone_project.entity.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private final String username;
    private final String nickname;
    private final String profileImageUrl;

    public MemberResponseDto(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
    }
}

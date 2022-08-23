package com.example.clone_project.dto.response;

import com.example.clone_project.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String profileImageUrl;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
    }
}

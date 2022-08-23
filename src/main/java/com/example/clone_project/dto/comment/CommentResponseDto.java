package com.example.clone_project.dto.comment;

import com.example.clone_project.entity.comment.Comment;
import com.example.clone_project.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {

    private Member member;

    private String content;


    public static CommentResponseDto fromEntity(Comment comment){

        return  new CommentResponseDto(comment.getMember(), comment.getContent());

    }
}
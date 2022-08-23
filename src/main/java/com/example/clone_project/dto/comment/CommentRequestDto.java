package com.example.clone_project.dto.comment;

import com.example.clone_project.entity.comment.Comment;
import com.example.clone_project.entity.common.Timestamped;
import com.example.clone_project.entity.member.Member;
import com.example.clone_project.entity.post.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentRequestDto extends Timestamped {

    private Long postId;

    private Member member;

    private String content;

    private String password;


    public Comment toEntity(Post post){
        return new Comment();

    }
}

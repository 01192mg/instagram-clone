package com.example.clone_project.service;

import com.example.clone_project.dto.request.CommentRequestDto;
import com.example.clone_project.dto.response.CommentResponseDto;
import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.entity.Comment;
import com.example.clone_project.entity.Member;
import com.example.clone_project.entity.Post;
import com.example.clone_project.repository.CommentRepository;
import com.example.clone_project.repository.MemberRepository;
import com.example.clone_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;


     //댓글 생성
     @Transactional // 댓글 작성
     public ResponseDto<?> createComment(Long postId, CommentRequestDto commentRequestDto, UserDetails userDetails) {
         Post post = postRepository.findById(postId)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
         Member member = memberRepository.findByUsername(userDetails.getUsername())
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
         Comment savedComment = commentRepository.save(Comment.builder()
                 .content(commentRequestDto.getContent())
                 .post(post)
                 .member(member)
                 .build());
         return ResponseDto.success(CommentResponseDto.fromEntity(savedComment));
     }

    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if(!comment.getMember().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
        comment.updateComment(commentRequestDto);
        return ResponseDto.success(CommentResponseDto.fromEntity(comment));
    }
    @Transactional
    public ResponseDto<?> deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if (!comment.getMember().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다");
        }
        commentRepository.deleteById(commentId);
        return ResponseDto.success(null);
    }

}

package com.example.clone_project.service;

import com.example.clone_project.dto.comment.CommentRequestDto;
import com.example.clone_project.entity.comment.Comment;
import com.example.clone_project.entity.comment.CommentRepository;
import com.example.clone_project.entity.member.Member;
import com.example.clone_project.entity.member.MemberRepository;
import com.example.clone_project.entity.post.Post;
import com.example.clone_project.entity.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     public ResponseEntity<?> createComment(Long postId, CommentRequestDto commentRequestDto, UserDetails userDetails) {
         Post post = postRepository.findById(postId)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
         Member member = memberRepository.getMemberByUsername(userDetails.getUsername())
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
         commentRepository.save(Comment.builder()
                 .content(commentRequestDto.getContent())
                 .post(post)
                 .member(member)
                 .build());
         return new ResponseEntity<>("댓글이 등록되었습니다", HttpStatus.CREATED);
     }

    @Transactional
    public ResponseEntity<String> updateComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if(!comment.getMember().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        comment.updateComment(commentRequestDto);
        commentRepository.save(comment);
        return new ResponseEntity<>("댓글이 수정되었습니다", HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if (!comment.getMember().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        commentRepository.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

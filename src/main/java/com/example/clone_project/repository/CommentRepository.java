package com.example.clone_project.repository;


import com.example.clone_project.entity.Comment;
import com.example.clone_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMember(Member member);
}

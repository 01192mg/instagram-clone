package com.example.clone_project.entity.likes;

import com.example.clone_project.entity.member.Member;
import com.example.clone_project.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes ,Long> {
    boolean existsByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);




}

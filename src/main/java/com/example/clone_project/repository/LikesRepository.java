package com.example.clone_project.repository;

import com.example.clone_project.entity.Member;
import com.example.clone_project.entity.Post;
import com.example.clone_project.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    boolean existsByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);




}

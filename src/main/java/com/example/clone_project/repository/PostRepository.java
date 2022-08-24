package com.example.clone_project.repository;

import com.example.clone_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.member m where m.username = :username")
    List<Post> findByUsername(@Param("username") String username);

    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByContentContaining(String content);

    Post findAllById(Long postId);
}
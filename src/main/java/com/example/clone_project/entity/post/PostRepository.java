package com.example.clone_project.entity.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.member m where m.username = :username")
    List<Post> findByUsername(@Param("username") String username);

    List<Post> findAllByOrderByModifiedAtDesc();

    Post findAllById(Long id);

    List<Post> findAllByContentLike (String content);

}

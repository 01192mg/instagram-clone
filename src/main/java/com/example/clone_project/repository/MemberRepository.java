package com.example.clone_project.repository;

import com.example.clone_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);

    Optional<Member> findByNickname(String nickname);
}

package com.example.clone_project.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getMembersByNickname(String username);
    Optional<Member> getMemberByUsername(String username);
}

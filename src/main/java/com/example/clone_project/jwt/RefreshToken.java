package com.example.clone_project.jwt;

import com.example.clone_project.entity.Member;
import com.example.clone_project.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class RefreshToken extends Timestamped {

  @Id
  @Column(nullable = false)
  private Long id;

  @JoinColumn(nullable = false)
  @OneToOne(fetch = FetchType.LAZY)
  private Member member;

  @Column(nullable = false)
  private String value;

  public void updateValue(String token) {
    this.value = token;
  }
}

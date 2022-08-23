package com.example.clone_project.entity;

import com.example.clone_project.dto.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@SuperBuilder
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Column(nullable = false)
    private String imageUrl;


    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getFile();
    }
}

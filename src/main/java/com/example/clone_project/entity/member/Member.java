package com.example.clone_project.entity.member;

import com.example.clone_project.entity.common.Timestamped;
import com.example.clone_project.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@SuperBuilder
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String username;

    private String nickname;

    private String password;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts;


}

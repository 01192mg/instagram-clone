package com.example.clone_project.entity.likes;

import com.example.clone_project.entity.common.Timestamped;
import com.example.clone_project.entity.post.Post;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class Image extends Timestamped {

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts;

}

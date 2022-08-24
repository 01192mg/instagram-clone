package com.example.clone_project.entity;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class Image extends Timestamped {

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts;

}

package com.blogapp.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post extends CreateUpdateInfo {
    @Id
    private Long id;

    private String title;

    private String summary;

    private String content;

    /**
     * writer of the post
     */
    @Column(nullable = false)
    private String nickname;
}

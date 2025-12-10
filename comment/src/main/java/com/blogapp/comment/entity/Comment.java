package com.blogapp.comment.entity;

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
public class Comment extends CreateUpdateInfo{
    @Id
    private long id;

    /**
     * writer of the post
     */
    @Column(nullable = false)
    private String nickname;

    private long postId;

    private String content;
}

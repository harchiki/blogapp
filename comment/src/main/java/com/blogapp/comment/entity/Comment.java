package com.blogapp.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    private long id;

    private long writerId;

    private long postId;

    private String content;
}

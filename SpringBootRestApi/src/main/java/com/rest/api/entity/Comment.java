package com.rest.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    
}

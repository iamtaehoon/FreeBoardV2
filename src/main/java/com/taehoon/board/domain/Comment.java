package com.taehoon.board.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    private int commentGroup;//그룹
    private int commentClass;//계층
    private int commentOrder;//순서
    // 그룹 순서대로 -> 그룹 안에서 계층이 낮은거부터 -> 낮은게 여러개면 거기서 순서.

}

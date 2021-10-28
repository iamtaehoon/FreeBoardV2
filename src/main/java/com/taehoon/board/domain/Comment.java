package com.taehoon.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class Comment {

    public Comment() {
    }

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private LocalDateTime registrationDate;

    private int commentGroup;//그룹
    private int commentClass;//계층
    private int commentOrder;//순서
    // 그룹 순서대로 -> 그룹 안에서 계층이 낮은거부터 -> 낮은게 여러개면 거기서 순서.
    // 실제 이제 화면에 렌더링 할때도, post_id 보고 전부 다 긁어옴. 거기서 그룹에따라 정렬, 다음 계층에 따라 정렬(들여쓰기), 다음 순서에 따라 정렬.


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment modifyContent(String content) {
        this.content = content;
        return this;
    }

    public Comment(String content, int commentGroup, int commentClass, int commentOrder, Member member, Post post) {
        this.content = content;
        this.commentGroup = commentGroup;
        this.commentClass = commentClass;
        this.commentOrder = commentOrder;
        this.member = member;
        this.post = post;
    }

}

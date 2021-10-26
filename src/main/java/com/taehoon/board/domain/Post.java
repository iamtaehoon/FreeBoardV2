package com.taehoon.board.domain;

import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Post {//게시물

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private Integer lookUp; //조회수 long으로 해줘야하나?

    private LocalDateTime registrationDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<AttachFile> files = new ArrayList<>();

    //연관관계 메서드 (자료 참조함.)
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    protected Post() {}

    //Post 생성 메서드.
    public Post(String title, String content, Member member) { // Member member도 들어가야 하지 않나? -> oo
        //id 자동 만들어줌.
        this.title = title;
        this.content = content;
        this.lookUp = 0;
        setMember(member);
        //registrationDate는 자동으로 만들어줌. -> 안만들어주는데 이거 직접 받으면 되나?
        //commnet,files도 자동으로 만들어주겠지?
    }

    //Post 수정 메서드.
    public Post modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

}

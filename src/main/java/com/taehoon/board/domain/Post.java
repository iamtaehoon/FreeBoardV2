package com.taehoon.board.domain;

import lombok.Getter;

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

    //연관관계 메서드 (자료 참조)
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
        //registrationDate는 자동으로 만들어줌.
        //commnet,files도 자동으로 만들어주겠지?
    }

    public Post modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

//    private Post(Long id, String title, String content, Integer lookUp, LocalDateTime registrationDate, Member member) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.lookUp = lookUp;
//        this.registrationDate = registrationDate;
//        setMember(member);
//
//    }
    // 이게 맞나는 모르겠는데 일단 이렇게 만들어보자.
    // 수정폼에서 하나하나 까서 modifyPost의 파라미터로 넣어준다.
    // 근데 기존 Post에서 id가 GeneratedValue인데 이렇게 값을 넣어줘도 되나?
    // -> 가능한듯. 찾아보니 기본 전략이 id가 null일때 db가 알아서 auto_increment해줌.
//    public static Post modifyPost(String title, String content, Post existingPost) {// 여기도 마찬가지로 파라미터로 Member member도 들어가야 하지 않나?
//
//        Long id = existingPost.getId();
//        Integer lookUp = existingPost.getLookUp();
//        LocalDateTime registrationDate = existingPost.getRegistrationDate();
//        Member member = existingPost.getMember();
//        return new Post(id, title, content, lookUp, registrationDate,member);
//    }

}

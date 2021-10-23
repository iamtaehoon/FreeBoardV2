package com.taehoon.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password; // 여기서 password는 그대로 헤더를 타고 들어오는게아니라. 클라에서 어떤 로직에 의해 변경해주고, 그걸 저장해줄거.
    //잘 모르겠는데 이거 리팩토링 할때쯔음 해서 패스워드 보안 관련 업그레이드 해주자. 일단 그냥 만들기

    private String email;
    private String phoneNum;
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date birth; //생년월일


    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

}

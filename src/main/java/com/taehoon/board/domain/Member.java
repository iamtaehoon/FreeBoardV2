package com.taehoon.board.domain;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {

    protected Member() {
    }

    public Member(String userId, String password, String email, String phoneNum, Gender gender, LocalDateTime birth) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.birth = birth;
    }

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password; // 여기서 password는 그대로 헤더를 타고 들어오는게아니라. 클라에서 어떤 로직에 의해 변경해주고, 그걸 저장해줄거.
    //잘 모르겠는데 이거 리팩토링 할때쯔음 해서 패스워드 보안 관련 업그레이드 해주자. 일단 그냥 만들기

    private String email;
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @Temporal(TemporalType.DATE) => 로컬데이트타임을 사용하면 필요 없음.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birth; //생년월일


    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();


}

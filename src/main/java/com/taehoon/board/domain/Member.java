package com.taehoon.board.domain;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Member {

    protected Member() {
    }

    public Member(String userId, String password, String email, String phoneNum, Gender gender, LocalDate birth) {
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
    private String password; // 여기서 password는 그대로 헤더를 타고 들어오는게아니라. 클라에서 어떤 로직에 의해 변경해주고, 그걸 저장해줄거. -> 클라 차원에서바뀌게 못하나? 지금은 컨트롤러 단위에서 바꿔줌.


    private String email;
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @Temporal(TemporalType.DATE) => 로컬데이트타임을 사용하면 필요 없음.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth; //생년월일


    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    public void encodePassword(String encodePassword) { // 이거 저기 맨 처음에 객체를 만들 때 도메인 내부에서 encode 로직을 실행시켜도 되는거 아냐?

        this.password = encodePassword;
    }
}

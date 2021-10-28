package com.taehoon.board.web.dto.member;

import com.taehoon.board.domain.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter @Setter
// DTO는 세터를 써도 될까? -> 이거 안쓰면 검증이 제대로 되나? 일단 setter로 만들고 그다음에 다른 방식 써보자.
public class MemberCreateDto {

    private String userId;
    private String password;

    private String email;
    private String phoneNum;

    private Gender gender;

    private LocalDateTime birth; //생년월일

}

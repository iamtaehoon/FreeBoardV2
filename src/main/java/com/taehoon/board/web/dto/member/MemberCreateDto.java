package com.taehoon.board.web.dto.member;

import com.taehoon.board.domain.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter
// DTO는 세터를 써도 될까? -> 이거 안쓰면 검증이 제대로 되나? 일단 setter로 만들고 그다음에 다른 방식 써보자.
// 정규 표현식?? 이거 공부해서 입력양식 내가 만들어보자.
public class MemberCreateDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 숫자, 영어 소문자, 대문자, 특수문자로 이루어진 8-20자의 문자어야 합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 양식이 잘못되었습니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{10,11}",message = "전화번호 양식이 잘못되었습니다.")
    private String phoneNum;

    @NotBlank(message = "성별을 선택해 주세요.")
    private String gender;

    @NotBlank(message = "생년월일을 선택해주세요")
//    @Past(message = "미래는 생년월일이 될 수 없습니다.")
    private String birth; //생년월일

}

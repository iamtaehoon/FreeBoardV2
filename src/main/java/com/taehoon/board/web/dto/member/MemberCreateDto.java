package com.taehoon.board.web.dto.member;

import com.taehoon.board.domain.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
// DTO는 세터를 써도 될까? -> 이거 안쓰면 검증이 제대로 되나? 일단 setter로 만들고 그다음에 다른 방식 써보자.
// 정규 표현식?? 이거 공부해서 입력양식 내가 만들어보자. -> 사실 필요한건 검색하면 다 나오는데, 읽을 수 있을 정도로만 공부하자. 만들수 있을 정도 x
public class MemberCreateDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 숫자, 영어 소문자, 대문자, 특수문자로 이루어진 8-20자의 문자어야 합니다.")
    private String password;

//    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 양식이 잘못되었습니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "(010[0-9]{8})|(011[0-9]{7})",message = "전화번호 양식이 잘못되었습니다.")
    //0~9까지 하나 선택 , 10개 혹은 11
    private String phoneNum;

    @NotBlank(message = "성별을 선택해 주세요.")
    private String gender;

    //    @Past(message = "미래는 생년월일이 될 수 없습니다.")
    //애는 왜 안되지?
    @NotBlank(message = "생년월일을 선택해주세요")
    // 음... 아무리 찾아봐도 form 내부 값을 객체로 바꾸는걸 못찾겠다.
    // String으로 받아와서, 양식이 맞나, 미래 값이 아니게 만드는건 진짜 엔티티에서 해줘야할듯.
    @Pattern(regexp = "(19|20)[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])",message = "정확한 날짜를 입력해주세요.")
    private String birth; //생년월일

}

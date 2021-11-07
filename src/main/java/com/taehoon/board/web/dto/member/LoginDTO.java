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
public class LoginDTO {

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

}

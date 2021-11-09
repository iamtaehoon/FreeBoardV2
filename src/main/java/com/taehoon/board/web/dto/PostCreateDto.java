package com.taehoon.board.web.dto;

import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class PostCreateDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    //첨부파일 추가

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

}

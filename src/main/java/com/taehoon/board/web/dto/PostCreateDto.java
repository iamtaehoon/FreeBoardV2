package com.taehoon.board.web.dto;

import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;


    public Post toEntity(Member member) {
        return new Post(title, content, member);
    }
}

package com.taehoon.board.web.dto;

import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import lombok.Getter;

@Getter
public class PostCreateDto {

    private String title;
    private String content;

    public PostCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity(Member member) {
        return new Post(title, content, member);
    }
}

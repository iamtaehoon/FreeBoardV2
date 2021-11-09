package com.taehoon.board.web.dto;

//postModifyDto? modifyPostDto? 이름 뭐가 맞을까.

import com.taehoon.board.domain.AttachFile;
import com.taehoon.board.domain.Comment;
import com.taehoon.board.domain.Member;
import com.taehoon.board.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostModifyDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content; //글 수정에는 이름, 내용만 있으면 되지.


}

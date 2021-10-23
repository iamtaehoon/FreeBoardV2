package com.taehoon.board.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AttachFile {
    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}

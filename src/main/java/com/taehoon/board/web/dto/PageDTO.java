package com.taehoon.board.web.dto;

import lombok.Getter;

@Getter
public class PageDTO {
//    private int startPage;
    private int endPage;
    private int thisPage;

    public PageDTO(int thisPage) {
        this.thisPage = thisPage;
        this.endPage = (int) Math.ceil(thisPage / 6.0) * 6;
//        this.startPage = this.endPage - 5;
    }

    public enum PagingStatus {
        FIRST, END, LEFT, RIGHT;
    }
}

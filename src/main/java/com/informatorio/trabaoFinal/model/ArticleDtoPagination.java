package com.informatorio.trabaoFinal.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDtoPagination {

    private String content;

    private int size;
    private int totalElements;
    private int page;
    private int totalPage;

    public ArticleDtoPagination() {
    }
}

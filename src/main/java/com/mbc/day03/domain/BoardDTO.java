package com.mbc.day03.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDTO {
    private int bid;
    private String subject;
    private String contents;
    private int hit;
    private String writer;
    private Date reg_date;
    private int replyCnt;
}

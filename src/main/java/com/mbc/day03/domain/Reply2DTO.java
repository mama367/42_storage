package com.mbc.day03.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Reply2DTO {
    private int rno;
    private int pid;

    private String r_contents;
    private String replyer;
    private Date r_date;
    private Date update_date;
    private int rating;
}

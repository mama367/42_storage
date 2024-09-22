package com.mbc.day03.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private int no;
    private String code;
    private int prodNo;
    private int userNo;
    private int prodQty;
    private Date orderDate;

}

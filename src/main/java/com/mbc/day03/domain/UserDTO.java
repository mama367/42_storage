package com.mbc.day03.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    private int no;
    private String name;
    private String id;
    private String password;
    private String email;
    private String tel;
    private String address;
    private String zip;
    private Timestamp date;
    private int privileges;
    private String birth;
    private int report;
}

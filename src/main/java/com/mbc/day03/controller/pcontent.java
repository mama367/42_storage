package com.mbc.day03.controller;

// Enum 정의
public enum pcontent {
    TaylorSwift("테일러 스위프트"),
    TWS("투어스"),
    NewJeans("뉴진스"),
    IVE("아이브");

    private final String value;

    pcontent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
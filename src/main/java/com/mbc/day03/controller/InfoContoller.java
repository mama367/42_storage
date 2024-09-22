package com.mbc.day03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/info")
public class InfoContoller {

    // 이용약관 페이지 이동
    @GetMapping("/agreement")
    public String agreement() {
        return "/info/agreement";
    }

    // 개인정보보호 페이지 이동
    @GetMapping("/privacy")
    public String privacy() {
        return "/info/privacy";
    }

    // 안내가이드 페이지 이동
    @GetMapping("/guide")
    public String guide() {
        return "/info/guide";
    }
}

package com.mbc.day03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingDTO {
    private Long no; // 상품번호
    private String name; // 상품명
    private int price; // 가격
    private String image; // 상품이미지
    private int userNo; // 판매자 번호
    private int groupNo; // 그룹번호
    private String categoryNo; // 카테고리 번호
    private int report; // 신고횟수
    private int score; // 별점
    private String pcontent; // 상품상세

}
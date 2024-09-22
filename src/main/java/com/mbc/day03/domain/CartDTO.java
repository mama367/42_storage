package com.mbc.day03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int No; //번호 cart_num
    private String id; //아이디 id
    private int productNo; //상품번호 pnum
    private int productQty; //상품수량 pqty
    private String Nm; //쇼핑 상품명 pnum
    private String PImage; //쇼핑 이미지 pimage
    private int Pr; //쇼핑 가격 price
    private String pcontent; //쇼핑 그룹 enum spec
    private int totalPr; //총합

    // 수량과 가격이 설정된 후 호출되어야 합니다.
    public void setTotal() {
        // 유효성 검사를 추가하여, 수량과 가격이 0 이상인지 확인합니다.
        if (productQty > 0 && Pr > 0) {
            this.totalPr = this.productQty * Pr;
        } else {
            // 적절한 기본값 설정 또는 예외 처리
            this.totalPr = 0;
        }
    }
}

package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartDao {

    // 카트 리스트
    List<CartDTO> cartList(String id);

    // 카트 등록
    void addCart(CartDTO dto);

    // 카트 리스트 체크(상품이 있는지 없는지)
    CartDTO checkCart(CartDTO dto);

    // 카트 삭제
    void deleteCart(int pNo);

    // 카트 삭제(id)**
    void delCartByUserId(String id);

    // 카트 수정
    void modifyCart(CartDTO dto);

    // 구매페이지에서 카트 삭제
    void deleteCheckout(Long productNo, String id);
}

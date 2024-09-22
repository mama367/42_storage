package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShoppingDao {

    // 상품 등록
    void insertShopping(ShoppingDTO product);

    // 상품 리스트
    List<ShoppingDTO> shoppingList();

    // 상품 삭제
    void deleteShopping(@Param("no") Long no);

    // 상품 조회
    ShoppingDTO getShopping(@Param("no") Long no);

    // 상품 수정
    void updateShopping(ShoppingDTO shoppingDTO);

    // 카테고리별 상품 리스트
    List<ShoppingDTO> getProdByCategory(@Param("code") String code);

    List<ShoppingDTO> getProdByGroup(int groupNo);

    // 스펙 - 상품별 리스트 (그룹별 리스트로 시도)
    ArrayList<ShoppingDTO> getProdByPcontent(String pcontent);

    // 카테고리별 상품 리스트 (페이지네이션 적용) (9월 9일)
    List<ShoppingDTO> getPaginatedProdByCategory(@Param("code") String code, @Param("offset") int offset, @Param("limit") int limit);

    // 페이지네이션을 적용한 상품 리스트 조회(9월 9일)
    List<ShoppingDTO> getPaginatedShoppingList(@Param("offset") int offset, @Param("limit") int limit);

    // 총 상품 개수 조회(9월 9일)
    int getTotalProductCount();

    // 이름으로 상품 검색 9월 10일, 9월 11일
    List<ShoppingDTO> searchProductsByName(@Param("name") String name);

//     판매자 상품 페이지 9월 11일, 9월 12일
    List<ShoppingDTO> getProductsByUser(int userNo);

}

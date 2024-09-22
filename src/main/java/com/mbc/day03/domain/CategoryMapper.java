package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper //DAO대신 사용
public interface CategoryMapper {

    //카테고리 등록
    void insertCategory(CategoryDTO dto);

    //카테고리 리스트
    ArrayList<CategoryDTO> categoryList();

    //카테고리 삭제
    void categoryDelete(int cnum);

    //카테고리 조회(dto랑 resultType="CategoryDTO" 이 쓰임)
    CategoryDTO catUpdate(int cnum);

    //카테고리 수정
    void catUpdateOk(CategoryDTO dto);

    // 카테고리별 상품 리스트 조회 메서드 추가 9월 16일
    List<ShoppingDTO> getShoppingListByCategory(String categoryNo);

}
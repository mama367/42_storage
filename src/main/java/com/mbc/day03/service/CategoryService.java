package com.mbc.day03.service;

import com.mbc.day03.domain.CategoryDTO;
import com.mbc.day03.domain.CategoryMapper;
import com.mbc.day03.domain.ShoppingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService{

    @Autowired
    private CategoryMapper mapper;

    // 카테고리 등록
    public void insertCategory(CategoryDTO dto) {
        mapper.insertCategory(dto);
    }

    // 카테고리 리스트 가져오기
    public List<CategoryDTO> categoryList() {
        return mapper.categoryList();
    }

    // 카테고리 삭제하기
    public void categoryDelete(int cnum) {
        mapper.categoryDelete(cnum);
    }

    // 카테고리 정보 조회하기
    public CategoryDTO catUpdate(int cnum) {
        return mapper.catUpdate(cnum);
    }

    // 카테고리 수정하기
    public void catUpdateOk(CategoryDTO dto) {
        mapper.catUpdateOk(dto);
    }

    // 카테고리 버튼화 9월 16일
    public List<ShoppingDTO> getShoppingListByCategory(String categoryNo) {
        return mapper.getShoppingListByCategory(categoryNo);
    }
}
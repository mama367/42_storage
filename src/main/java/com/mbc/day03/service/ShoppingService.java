package com.mbc.day03.service;

import com.mbc.day03.domain.ShoppingDTO;
import com.mbc.day03.domain.ShoppingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingDao shoppingDao;

    @Autowired
    private CategoryService categoryService; // CategoryService 주입

    // 상품 등록
    public void insertShopping(ShoppingDTO product) {
        shoppingDao.insertShopping(product);
    }

    // 상품 전체 리스트
    public List<ShoppingDTO> shoppingList() {
        return shoppingDao.shoppingList();
    }

    // 상품 삭제
    public void deleteShopping(int no) {
        shoppingDao.deleteShopping((long) no);
    }

    // 상품 정보 조회
    public ShoppingDTO getShopping(Long no) {
        return shoppingDao.getShopping(no);
    }

    // 상품 수정
    public void updateShopping(ShoppingDTO shoppingDTO) {
        shoppingDao.updateShopping(shoppingDTO);
    }

    // 카테고리별 상품 리스트
    public List<ShoppingDTO> getProdByCategory(String code) {
        return shoppingDao.getProdByCategory(code);
    }

    public List<ShoppingDTO> getProdByGroup(int groupNo){ return shoppingDao.getProdByGroup(groupNo); }

    // 스펙 - 상품별 리스트(그룹 리스트로 생각)
    public ArrayList<ShoppingDTO> getProdByPcontent(String pcontent) {
        return shoppingDao.getProdByPcontent(pcontent);
    }

    // 페이지네이션을 적용한 상품 리스트 조회 9월 9일
    public List<ShoppingDTO> getPaginatedShoppingList(int page, int size) {
        int offset = (page - 1) * size;
        return shoppingDao.getPaginatedShoppingList(offset, size);
    }

    // 총 페이지 수 계산 9월 9일
    public int getTotalPages(int size) {
        int totalProducts = shoppingDao.getTotalProductCount();
        return (int) Math.ceil((double) totalProducts / size);
    }

    // 상품명으로 검색 9월 10일, 9월 11일
    public List<ShoppingDTO> searchProductsByName(String name) {
        return shoppingDao.searchProductsByName(name);
    }

    //판매자 전용 페이지 9월 11일, 9월 12일
    public List<ShoppingDTO> getProductsByUser(int userNo) {
        return shoppingDao.getProductsByUser(userNo);
    }


}

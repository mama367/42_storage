package com.mbc.day03.service;

import com.mbc.day03.domain.CartDTO;
import com.mbc.day03.domain.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

//    @Autowired
//    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 카트 리스트 가져오기 (구매 페이지에서 사용)
    public List<CartDTO> cartList(String id) {
        return cartDao.cartList(id);
    }

    // 카트 등록
    public void addCart(CartDTO dto) {
        cartDao.addCart(dto);
    }

    // 카트 리스트 체크(상품이 있는지 없는지)
    public CartDTO checkCart(CartDTO dto) {
        return cartDao.checkCart(dto);
    }

    // 카트 삭제
    public void deleteCart(int pNo) { cartDao.deleteCart(pNo); }

    // 카트 삭제(ID)**
    public void delCartByUserId(String id) {
        cartDao.delCartByUserId(id);
    }

    // 카트 업데이트
    public void modifyCart(CartDTO dto) {
        cartDao.modifyCart(dto);
    }

    // 카트 삭제 (구매 페이지에서)
    public void deleteCheckout(Long productNo, String id) {
        cartDao.deleteCheckout(productNo, id);
    }

}

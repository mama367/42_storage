package com.mbc.day03.controller;

import com.mbc.day03.domain.CartDTO;
import com.mbc.day03.domain.OrderDTO;
import com.mbc.day03.domain.ShoppingDTO;
import com.mbc.day03.domain.UserDTO;
import com.mbc.day03.service.CartService;
import com.mbc.day03.service.OrderService;
import com.mbc.day03.service.ShoppingService;
import com.mbc.day03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingService shoppingService;

    // 카테고리 리스트 띄우기
    //장바구니 수량 증가 추가 (9월 7일)
    @RequestMapping(value = "/cart/cartList.do", method = RequestMethod.GET)
    public String cartList(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO dto = (UserDTO) session.getAttribute("mvo");

        if (dto == null) {
            redirectAttributes.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }

        String id = dto.getId();
        ArrayList<CartDTO> cartList = (ArrayList<CartDTO>) cartService.cartList(id);

        int cartTotPr = 0;
        for (CartDTO cdto : cartList) {
            cdto.setTotal();
            cartTotPr += cdto.getTotalPr();
        }

        model.addAttribute("dtos", cartList);
        model.addAttribute("cartTotPr", cartTotPr);

        return "cart/cart_list";
    }



    // 카트 등록 OK - Post방식
    @RequestMapping(value = "/cart/addCart.do", method = RequestMethod.POST)
    public String addCart(CartDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO mdto = (UserDTO) session.getAttribute("mvo");
        if (mdto == null) {
            redirectAttributes.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }

        dto.setId(mdto.getId());
        CartDTO existingItem = cartService.checkCart(dto);

        if (existingItem == null) {
            dto.setProductQty(1);  // 새 상품은 수량을 1로 설정
            cartService.addCart(dto);
            redirectAttributes.addFlashAttribute("msg", "장바구니에 상품이 추가되었습니다.");
        } else {
            existingItem.setProductQty(existingItem.getProductQty() + 1);  // 기존 상품은 수량을 1 증가
            cartService.modifyCart(existingItem);
            redirectAttributes.addFlashAttribute("msg", "장바구니 상품 수량이 증가되었습니다.");
        }

        return "redirect:/cart/cartList.do";
    }



    // 카트 등록 OK 상품 상세보기 페이지에서 담아옴 - Get방식 (중복상품이 담아지지 않는 오류가 있었음)
//    @RequestMapping(value = "/cart/addCart2.do")
//    public String addCart2(int productNo, int productQty, String userID, RedirectAttributes rttr) {
//
//
//        // CartDTO 객체 생성 및 사용자 ID 설정
//        CartDTO cartDto = new CartDTO();
//        cartDto.setId(userID);  // 사용자 ID 설정
//        cartDto.setProductNo(productNo);
//        cartDto.setProductQty(productQty);
//
//        rttr.addAttribute("userID", userID);
//
//        // 장바구니에 추가
//        service.addCart(cartDto);
//        System.out.println("cartDto = " + cartDto);
//
//        // 장바구니 목록 페이지로 리디렉션
//        return "redirect:/cart/cartList.do";
//    }

    // 카트 등록 OK 상품 상세보기 페이지에서 담아옴 - Get방식 (장바구니 중복 담기 수정 완료 9월 9일)
    @RequestMapping(value = "/cart/addCart2.do")
    public String addCart2(int productNo, int productQty, String userID, RedirectAttributes rttr) {

        // 사용자 ID 설정
        CartDTO newCartDto = new CartDTO();
        newCartDto.setId(userID);
        newCartDto.setProductNo(productNo);
        newCartDto.setProductQty(productQty);

        // 현재 장바구니에서 사용자 ID로 모든 항목 가져오기
        ArrayList<CartDTO> cartList = (ArrayList<CartDTO>) cartService.cartList(userID);
        boolean itemExists = false;

        // 기존 장바구니에서 중복된 항목 찾기
        for (CartDTO cartDto : cartList) {
            if (cartDto.getProductNo() == productNo) {

                // 중복된 항목이 발견된 경우
                cartDto.setProductQty(cartDto.getProductQty() + productQty);
                cartService.modifyCart(cartDto); // 수량 업데이트
                itemExists = true;
                break;
            }
        }

        // 중복되지 않은 항목인 경우 장바구니에 추가
        if (!itemExists) {
            cartService.addCart(newCartDto);
        }

        rttr.addAttribute("userID", userID);
        return "redirect:/cart/cartList.do";
    } //if 절을 넣어서 중복된 경우, 중복되지 않는 경우를 나눠서 넣어야한다. (9월 9일)


    // 카트 리스트에서 삭제 OK
    @RequestMapping(value = "/cart/deleteCart.do", method = RequestMethod.POST)
    public String deleteCart(@RequestParam("pNo") Long no, RedirectAttributes redirectAttributes) {
        cartService.deleteCart(Math.toIntExact(no));
        redirectAttributes.addFlashAttribute("msg", "장바구니에서 상품이 삭제되었습니다.");
        return "redirect:/cart/cartList.do";
    }

    // 카트 정보(인포)폼에서 수정 OK 이동
    @RequestMapping(value = "/cart/modifyCart.do", method = RequestMethod.POST)
    public String modifyCart(CartDTO dto, RedirectAttributes redirectAttributes) {
        cartService.modifyCart(dto);
        redirectAttributes.addFlashAttribute("msg", "장바구니 상품이 수정되었습니다.");
        return "redirect:/cart/cartList.do";
    }

    // 구매하기 폼으로 가기(info)
    @RequestMapping(value = "/cart/checkout.do", method = RequestMethod.GET)
    public String checkout(Model model, HttpSession session, HttpServletRequest request) {
        //9월 6일 UserDTO를 mdto로 지칭
        UserDTO mdto = (UserDTO) session.getAttribute("mvo");

        if (mdto == null) {
            request.setAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/user/login";
        }

        String id = mdto.getId();
        ArrayList<CartDTO> cartList = (ArrayList<CartDTO>) cartService.cartList(id);

        int cartTotPr = 0;
        for (CartDTO dto : cartList) {
            cartTotPr += dto.getPr() * dto.getProductQty();
        }


        model.addAttribute("cartTotPr", cartTotPr);
        model.addAttribute("cartList", cartList);

        int no = -1;
        if (mdto.getNo() != -1) {  // 변경된 필드명
            no = mdto.getNo();
        }

        UserDTO mDto = userService.userInfo(no);
        if (mDto == null) {
            mDto = new UserDTO();
            mDto.setName("기본 사용자");
        }

        model.addAttribute("mDto", mDto);

        return "cart/checkout";
    }

    // 구매폼 리스트에서 삭제
    @RequestMapping(value = "/cart/deleteCheckout.do", method = RequestMethod.POST)
    public String deleteCheckout(@RequestParam("delproductNos") String delProductNos, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO mdto = (UserDTO) session.getAttribute("mvo");
        String id = mdto.getId();

        if (delProductNos == null || delProductNos.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "잘못된 경로입니다.");
            return "redirect:/cart/cartList.do";
        }

        String[] numArr = delProductNos.split(",");
        for (String num : numArr) {
            try {
                cartService.deleteCheckout(Long.parseLong(num), id);  // 변경된 타입
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("msg", "잘못된 번호 포맷입니다.");
                return "redirect:/cart/cartList.do";
            }
        }

        ArrayList<CartDTO> cartList = (ArrayList<CartDTO>) cartService.cartList(id);
        for (CartDTO cDto : cartList) {
            cDto.setTotal();
        }

        session.setAttribute("dtos", cartList);
        redirectAttributes.addFlashAttribute("msg", "장바구니 삭제 완료");
        return "redirect:/cart/checkout.do";
    }

    // 결제완료 창으로 넘어감 **
    @RequestMapping("/order")
    public String order_confirm(@RequestParam("userNo")int userNo, @RequestParam("userId")String userId, Model model){

        ArrayList<CartDTO> cartList = (ArrayList<CartDTO>) cartService.cartList(userId);

        model.addAttribute("cartList", cartList);
        System.out.println("cartList = " + cartList);

        String code = UUID.randomUUID().toString().substring(0, 10);

        ArrayList<ShoppingDTO> prodList = new ArrayList<>();

        int priceTotal = 0;

        for(CartDTO c:cartList){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCode(code);
            orderDTO.setUserNo(userNo);
            orderDTO.setProdNo(c.getProductNo());
            orderDTO.setProdQty(c.getProductQty());
            orderService.addOrder(orderDTO);

            ShoppingDTO prod = shoppingService.getShopping((long) c.getProductNo());

            prodList.add(prod);
            priceTotal += prod.getPrice() * c.getProductQty();
        }

        System.out.println("prodList = " + prodList);

        model.addAttribute("prodList", prodList);

        System.out.println("priceTotal = " + priceTotal);

        model.addAttribute("priceTotal", priceTotal);

        cartService.delCartByUserId(userId);

        List<OrderDTO> orderList = orderService.orderList(userNo);

        model.addAttribute("orderList", orderList);

        Map<String, Object> user = userService.getListOne(userNo);

        model.addAttribute("user", user);

        return "/cart/order_confirm";
    }
}
package com.mbc.day03.controller;

import com.mbc.day03.domain.UserDTO;
import com.mbc.day03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    // 사용자 리스트 페이지 이동
    @GetMapping("/user")
    public String user(Model model) {
        List<Map<String, Object>> list = userService.getList();
        model.addAttribute("list", list);
        return "/user/user";
    }

    // 사용자 회원가입 페이지 이동
    @GetMapping("/user/register")
    public String userRegister(Model model) {
        List<Map<String, Object>> list = userService.getList();
        model.addAttribute("list", list);
        return "/user/register";
    }

    // 관리자 회원가입 페이지 이동
    @GetMapping("/admin/register")
    public String adminRegister(Model model) {
        List<Map<String, Object>> list = userService.getList();
        model.addAttribute("list", list);
        return "/admin/register";
    }

    // 사용자 회원가입 정보 백엔드 보내기
    @PostMapping("/user/register")
    public String userRegister(@RequestParam("name")String name,
                               @RequestParam("id")String id,
                               @RequestParam("password")String password,
                               @RequestParam("email")String email,
                               @RequestParam("tel")String tel,
                               @RequestParam("address")String address,
                               @RequestParam("zip")String zip,
                               @RequestParam("birth")String birth,
                               @RequestParam("request")int request,
                               @RequestParam("privileges")int privileges){
        int n = userService.PuserRegister(name, id, password, email, tel, address, zip, birth, request, privileges);
        return "redirect:/user/login";
    }

    // 관리자 회원가입 정보 백엔드 보내기
    @PostMapping("/admin/register")
    public String adminRegister(@RequestParam("name")String name,
                                @RequestParam("id")String id,
                                @RequestParam("password")String password,
                                @RequestParam("email")String email,
                                @RequestParam("tel")String tel,
                                @RequestParam("address")String address,
                                @RequestParam("zip")String zip,
                                @RequestParam("birth")String birth,
                                @RequestParam("request")int request,
                                @RequestParam("privileges")int privileges){
        int n = userService.PadminRegister(name, id, password, email, tel, address, zip, birth, request, privileges);
        return "redirect:/user/login";
    }

    // 회원 수정 페이지 불러오기
    @GetMapping("/user/modify")
    public String userModify(@RequestParam("no") int no, Model model) {
        Map<String, Object> map = userService.getListOne(no);
        model.addAttribute("map", map);
        return "user/modify";
    }

    // 수정된 회원 정보 백엔드 보내기
    @PostMapping("/user/modify")
    public String userModify(@RequestParam("no")int no,
                             @RequestParam("name")String name,
                             @RequestParam("id")String id,
                             @RequestParam("email")String email,
                             @RequestParam("tel")String tel,
                             @RequestParam("address")String address,
                             @RequestParam("zip")String zip,
                             @RequestParam("birth")String birth){
        int n = userService.PuserModify(no, name, id, email, tel, address, zip, birth);
        return "redirect:/user";
    }

    // 회원 로그인 페이지 이동
    @GetMapping("/user/login")
    public String userLogin() {
        return "/user/login";
    }

    // 로그인 정보 백엔드 보내기
    @PostMapping("/user/login")
    public String userLogin(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpServletRequest request) {
        boolean loginSuccess = userService.PuserLogin(id, password, request);

        if (loginSuccess) {
            HttpSession session = request.getSession();
            UserDTO userDTO = userService.getUserById(id); // Retrieve user info
            session.setAttribute("mvo", userDTO); // Save user info in session
            return "redirect:/";
        } else {
            return "redirect:/user/login?error";
        }
    }

    // 로그아웃 기능 추가
    @PostMapping("/user/logout")
    public String userLogout(HttpServletRequest request) {
        boolean logoutSuccess = userService.userLogout(request);

        if (logoutSuccess) {
            System.out.println("로그아웃 성공");
            return "redirect:/";
        }else {
            System.out.println("로그아웃 실패");
            return "redirect:/";
        }
    }

    // 마이페이지 정보보기 페이지 이동
    @GetMapping("/user/mypage")
    public String userMypage(@RequestParam("no")int no, Model model) {
        Map<String, Object> map = userService.getListOne(no);
        model.addAttribute("map", map);
        return "user/mypage";
    }

    // 마이페이지 수정페이지 이동
    @GetMapping("/user/mypageUpdate")
    public String userMypageUpdate(@RequestParam("no")int no,Model model) {
        Map<String, Object> map = userService.getListOne(no);
        model.addAttribute("map", map);
        return "/user/mypageUpdate";
    }

    // 판매자 권한 요청 정보 백엔드 보내기
    @PostMapping("/user/privileges")
    public String userPrivileges(@RequestParam("no")int no,
                                 @RequestParam("request")int request){
        int n = userService.PuserPrivileges(no, request);
        return "redirect:/user/mypage?no="+no;
    }

    // 비밀번호 변경페이지 이동
    @GetMapping("/user/chPw")
    public String userChpw(@RequestParam("no")int no,Model model) {
        Map<String, Object> map = userService.getListOne(no);
        model.addAttribute("map", map);
        return "/user/chPw";
    }

    // ★수정된 비밀번호 정보 백엔드 보내기
    @PostMapping("/user/chpw")
    public String userChpw(@RequestParam("no")int no,
                           @RequestParam("password")String password,
                           @RequestParam("newpassword")String newpassword,
                           HttpServletRequest request){

        boolean Chpw = userService.PChpw(no, password, newpassword);

        if (Chpw) {
            HttpSession session = request.getSession();
            String dbPw = userService.getPwByNo(no);
            session.setAttribute("dbPw", dbPw);
            return "redirect:/";
        } else {
            return "redirect:/user/chPw?no="+no+"&error";
        }
    }

    // 수정된 회원 정보 백엔드 보내기
    @PostMapping("/user/mypage")
    public String userMypage(@RequestParam("no")int no,
                             @RequestParam("name")String name,
                             @RequestParam("email")String email,
                             @RequestParam("tel")String tel,
                             @RequestParam("address")String address,
                             @RequestParam("zip")String zip,
                             @RequestParam("birth")String birth){
        int n = userService.PuserMypage(no, name, email, tel, address, zip, birth);
        return "redirect:/user/mypage?no="+no;
    }

    // 회원 삭제 페이지 불러오기
    @GetMapping("/user/delete")
    public String userDelete(@RequestParam("no") int no){
        int n = userService.userDelete(no);
        return "redirect:/user";
    }

    // 회원 목록 선택 삭제 백엔드 보내기
    @PostMapping("/user/delete")
    public String userDelete(@RequestParam("chkNo") List<String> chkList){
        if (chkList != null){
            int n = userService.PuserDelete(chkList);
        }
        return "redirect:/user";
    }

    // 회원 권한 신청 페이지 이동
    @GetMapping("/user/request")
    public String userRequest(Model model){
        List<Map<String, Object>> list = userService.getList();
        model.addAttribute("list", list);
        return "/user/request";
    }

    // 판매자 권한 수락 정보 백엔드 전송
    @GetMapping("/user/accept")
    public String userAccept(@RequestParam("no")int no){
        int n = userService.userAccept(no);
        return "redirect:/user/request";
    }
    // 판매자 권한 반려 정보 백엔드 전송
    @GetMapping("/user/reject")
    public String userReject(@RequestParam("no")int no){
        int n = userService.userReject(no);
        return "redirect:/user/request";
    }

    // ★회원가입 약관동의 페이지 이동
    @GetMapping("/user/Agreement")
    public String userAgreement(){
        return "/user/Agreement";
    }
}

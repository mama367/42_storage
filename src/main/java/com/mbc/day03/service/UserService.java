package com.mbc.day03.service;

import com.mbc.day03.domain.UserDAO;
import com.mbc.day03.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDAO userdao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 유저 정보 추가
    public UserDTO userInfo(int no) {
        return userdao.userInfo(no);  // UserDAO를 통해 사용자 정보 가져오기
    }

    public List<Map<String, Object>> getList(){
        return userdao.getList();
    }

    public int PuserRegister(String name, String id, String password, String email, String tel, String address, String zip, String birth, int request, int privileges){
        // 암호화 후 입력하기
        password = (passwordEncoder.encode(password));
        return userdao.userRegister(name, id, password, email, tel, address, zip, birth, request, privileges);
    }

    // 관리자 권한 정보 백엔드 보내기
    public int PuserPrivileges(int no, int request){
        return userdao.userPrivileges(no, request);
    }

    public int PadminRegister(String name, String id, String password, String email, String tel, String address, String zip, String birth, int request, int privileges){
        // 암호화 후 입력하기
        password = (passwordEncoder.encode(password));
        return userdao.adminRegister(name, id, password, email, tel, address, zip, birth, request, privileges);
    }

    public Map<String, Object> getListOne(int no) {
        return userdao.getListOne(no);
    }

    // 수정 정보 백엔드 전송
    public int PuserModify(int no, String name, String id, String email, String tel, String address, String zip, String birth){
        return userdao.userModify(no, name, id, email, tel, address, zip, birth);
    }

    // ★비밀번호 수정 백엔드 전송
    public boolean PChpw(int no, String password, String newpassword){
        // 번호와 일치하는 회원 비밀번호 가져오기
        String dbPw = userdao.getPwByNo(no);

        if (dbPw != null) {
            if (passwordEncoder.matches(password, dbPw)) {
                System.out.println("비밀번호 일치.");
                newpassword = (passwordEncoder.encode(newpassword));
                int n = userdao.chpw(no, newpassword);
                return true;
            }
        }
        System.out.println("비밀번호 불일치.");
        return false;
    }

    // 로그인 하기
    public boolean PuserLogin(String id, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 아이디와 일치하는 회원정보 가져오기
        Map<String, Object> userInfo = userdao.userLogin(id);

        if (userInfo != null) {
            String dbPassword = userdao.getPw(id);

            // 비밀번호 일치 확인
            if (passwordEncoder.matches(password, dbPassword)) {
                // 로그인 성공: 세션에 사용자 정보 저장
                session.setAttribute("userId", userInfo.get("id"));
                session.setAttribute("userName", userInfo.get("name"));

                int userNo = Integer.parseInt(userInfo.get("no").toString());
                session.setAttribute("userNo", userNo);

                int userPrivileges = Integer.parseInt(userInfo.get("privileges").toString());
                session.setAttribute("userPrivileges", userPrivileges);

                // 필요한 다른 사용자 정보도 세션에 저장할 수 있습니다
                System.out.println("로그인 성공.");
                return true;
            }
        }

        System.out.println("로그인 실패.");
        return false;
    }

    // 로그아웃 하기
    public boolean userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            System.out.println("로그아웃 성공");
            return true;
        }else {
            System.out.println("로그아웃 실패");
            return false;
        }
    }

    // ★마이페이지 수정 정보 백엔드에 전송
    public int PuserMypage(int no, String name, String email, String tel, String address, String  zip, String birth){
        return userdao.userMypage(no, name, email, tel, address, zip, birth);
    }

    // 유저 삭제 페이지 이동
    public int userDelete(int no){
        return userdao.userDelete(no);
    }

    // 유저 삭제 정도 백엔드에 전송
    public int PuserDelete(List<String> chkList){
        return userdao.PuserDelete(chkList);
    }

    // 유저 권한 신청 정보 백엔드에 전송
    public int userAccept(int no){
        return userdao.userAccept(no);
    }

    // 유저 권한 거부 정보 백엔드에 전송
    public int userReject(int no){
        return userdao.userReject(no);
    }

    // 사용자의 정보를 가져오는 getUserById 메소드
    public UserDTO getUserById(String id) {
        return userdao.getUserById(id);  // UserDAO를 통해 사용자 정보를 가져옵니다.
    }

    // ★사용자의 정보를 가져오는 getUserByNo 메소드
    public String getPwByNo(int no) {
        return userdao.getPwByNo(no);
    }
}
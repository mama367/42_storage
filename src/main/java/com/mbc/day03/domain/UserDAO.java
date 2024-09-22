package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
    List<Map<String, Object>> getList();

    // 관리자 권한 정보 백엔드 보내기
    int userPrivileges(int no,
                       int request);

    int userRegister(String name,
                     String id,
                     String password,
                     String email,
                     String tel,
                     String address,
                     String zip,
                     String birth,
                     int request,
                     int privileges);

    int adminRegister(String name,
                      String id,
                      String password,
                      String email,
                      String tel,
                      String address,
                      String zip,
                      String birth,
                      int request,
                      int privileges);

    int userDelete(int no);

    int PuserDelete(List<String> chkList);

    Map<String, Object> getListOne(int no);

    /*회원 수정 */
    int userModify(int no,
                   String name,
                   String id,
                   String email,
                   String tel,
                   String address,
                   String zip,
                   String birth);

    /* 비밀번호 변경 */
    int chpw(int no,
             String password);

    List<Map<String, Object>> userSearch(String name, String groupName);

    Map<String, Object> userLogin(String id);

    String getPw(String id);

    // 마이페이지 정보 수정 백엔드 보내기
    int userMypage(int no, String name,  String email, String tel, String address, String  zip, String birth);

    int userAccept(int no);

    int userReject(int no);

    //   userinfo 추가
    UserDTO userInfo(int no);

    // id 대조 추가
    UserDTO getUserById(String id);

    // ★no로 pw 가져오기 대조 추가
    String getPwByNo(int no);
}
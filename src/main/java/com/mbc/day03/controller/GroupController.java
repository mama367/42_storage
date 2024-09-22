package com.mbc.day03.controller;

import com.mbc.day03.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class GroupController {
    @Autowired
    GroupService groupService;

    // 그룹 리스트 페이지 이동
    @GetMapping("/group")
    public String group(Model model) {
        List<Map<String, Object>> list = groupService.getList();
        model.addAttribute("list", list);
        return "/group/group";
    }

    // 그룹 등록 페이지 이동
    @GetMapping("/group/register")
    public String groupRegister(){
        return "/group/register";
    }

    // 그룹 등록 정보 백엔드 보내기
    @PostMapping("/group/register")
    public String groupRegister(@RequestParam("no")int no,
                                @RequestParam("name")String name,
                                @RequestParam("debut")String debut,
                                @RequestParam("info")String info,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam("message")String message){

        String path = "C:/proj_files/images/";

        String filename_url = image.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + filename_url;
        String filepath = path + "\\" + filename;

        int n = groupService.PgroupRegister(no, name, debut, info, filename, message);

        File file = new File(filepath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(image.getBytes());
            bos.close();

        } catch (Exception e) {
            System.out.println("=======================Fail=======================");
            return "redirect:/group";
        }

        return "redirect:/group";
    }

    // 그룹 삭제 페이지 이동
    @GetMapping("/group/delete")
    public String groupDelete(@RequestParam("no")int no){
        int n = groupService.groupDelete(no);
        return "redirect:/group";
    }

    // 그룹 수정 페이지 이동
    @GetMapping("/group/modify")
    public String groupModify(@RequestParam("no")int no, Model model){
        Map<String, Object> map = groupService.getListOne(no);
        model.addAttribute("map", map);
        return "/group/modify";
    }

    // 그룹 수정 정보 백엔드 보내기
    @PostMapping("/group/modify")
    public String PgroupModify(@RequestParam("no") int no,
                               @RequestParam("name")String name,
                               @RequestParam("debut")String debut,
                               @RequestParam("info")String info,
                               @RequestParam("image")MultipartFile image,
                               @RequestParam("oldImage") String oldImage,
                               @RequestParam("message")String message){

        String path = "C:/proj_files/images/";

        String filename_url = image.getOriginalFilename();
        String filename = "";
        String filepath = "";

        if(filename_url == null || filename_url.trim().equals("")){
            filename = oldImage;
            filepath = path + "\\" + oldImage;

            int n = groupService.PgroupModify(no, name, debut, info, filename, message);

            return "redirect:/group";
        } else {
            filename = System.currentTimeMillis() + "_" + filename_url;
            filepath = path + "\\" + filename;

            int n = groupService.PgroupModify(no, name, debut, info, filename, message);

            File file = new File(filepath);

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(image.getBytes());
                bos.close();

            } catch (Exception e) {
                System.out.println("=======================Fail=======================");
                return "redirect:/group";
            }

            return "redirect:/group";

        }

    }

    // 그룹 목록 선택 삭제 백엔드 보내기
    @PostMapping("/group/delete")
    public String PgroupDelete(@RequestParam("chkNo") List<String> chkList){
        if (chkList != null){
            int n = groupService.PgroupDelete(chkList);
        }
        return "redirect:/group";
    }

    // 그룹 검색정보 백엔드 보내기
    @PostMapping("/group/search")
    public String groupSearch(@RequestParam(value="groupName", defaultValue="all")String groupName,
                              @RequestParam("name")String name,
                              Model model){
        List<Map<String, Object>> List = groupService.groupSearch(groupName, name);
        model.addAttribute("list", List);
        return "/group/group";
    }
}
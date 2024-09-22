package com.mbc.day03.controller;

import com.mbc.day03.domain.BoardDTO;
import com.mbc.day03.domain.CategoryDTO;
import com.mbc.day03.domain.PageDTO;
import com.mbc.day03.domain.ShoppingDTO;
import com.mbc.day03.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String root(Locale locale, Model model) {
        // 서버 시간 가져오기
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);

        // 모델에 서버 시간 추가
        model.addAttribute("serverTime", formattedDate);

        // 카테고리 리스트 가져오기
        List<CategoryDTO> categoryList = categoryService.categoryList();

        // 카테고리 리스트를 모델에 추가
        model.addAttribute("categorylist", categoryList);

        List<Map<String, Object>> artistList = artistService.getList();
        model.addAttribute("artistList", artistList);

        List<Map<String, Object>> groupList = groupService.getList();
        model.addAttribute("groupList", groupList);

        return "home/home"; // 메인 페이지로 이동
    }

    @GetMapping("/artistView")
    public String artistView(Model model, PageDTO pDto, int no){
        Map<String, Object> groupMap = groupService.getListOne(no);
        model.addAttribute("group", groupMap);

        List<Map<String, Object>> artistMap = artistService.groupArtist(no);

        List<ShoppingDTO> prodList = shoppingService.getProdByGroup(no);
        model.addAttribute("prodList", prodList);

        List<BoardDTO> noticeList = boardService.getListByArtist(pDto, 0, no);
        model.addAttribute("noticeList", noticeList);

        List<BoardDTO> communityList = boardService.getListByArtist(pDto, 1, no);
        model.addAttribute("communityList", communityList);

        List<BoardDTO> noticeTop = boardService.getTopListByArtist(pDto, 0, no);
        model.addAttribute("noticeTop", noticeTop);

        List<BoardDTO> communityTop = boardService.getTopListByArtist(pDto, 1, no);
        model.addAttribute("communityTop", communityTop);

        /*if(artistMap != null) {

        }else{
            model.addAttribute("artist", null);
        }*/
        model.addAttribute("artist", artistMap);
        return "/artist/artistView";
    }

//
}
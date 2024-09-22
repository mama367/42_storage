package com.mbc.day03.controller;

import com.mbc.day03.domain.CategoryDTO;
import com.mbc.day03.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //카테고리 리스트 띄우기
    @GetMapping("/category")
    public String categoryList(Model model) {
        List<CategoryDTO> categoryList = categoryService.categoryList();
        model.addAttribute("list", categoryList);
        return "category/category_list";
    }

    // 카테고리 등록 폼으로 이동
    @GetMapping("/category/input")
    public String catInputForm() {
        return "category/category_input";
    }

    //카테고리 등록 ok - post 방식
    @PostMapping("/category/save")
    public String insertCategory(CategoryDTO dto, Model model, RedirectAttributes redirectAttributes) {
        if (dto.getCcode() == null || dto.getCat_name() == null ||
                dto.getCcode().trim().isEmpty() || dto.getCat_name().trim().isEmpty()) {
            model.addAttribute("msg", "카테고리 등록 실패");
            return "category/category_list";
        }

        categoryService.insertCategory(dto);
        redirectAttributes.addFlashAttribute("msg", "카테고리 등록 완료");
        return "redirect:/category";
    }


    //카테고리 리스트 삭제하기
    @PostMapping("/category/delete")
    public String categoryDelete(int cnum) {
        categoryService.categoryDelete(cnum);
        return "redirect:/category";
    }

    // 카테고리 정보 폼으로 가기
    @GetMapping("/category/edit")
    public String catUpdate(int cnum, Model model) {
        CategoryDTO dto = categoryService.catUpdate(cnum);
        model.addAttribute("dto", dto);
        return "category/category_update";
    }

    // 카테고리 정보에서 수정 이동
    @PostMapping("/category/update")
    public String catUpdateOk(CategoryDTO dto, RedirectAttributes redirectAttributes) {
        if (dto.getCcode() == null || dto.getCat_name() == null ||
                dto.getCcode().trim().isEmpty() || dto.getCat_name().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "카테고리 수정 실패");
            return "redirect:/category";
        }

        categoryService.catUpdateOk(dto);
        redirectAttributes.addFlashAttribute("msg", "카테고리 수정 완료");
        return "redirect:/category";
    }
}

package com.mbc.day03.controller;

import com.mbc.day03.domain.CategoryDTO;
import com.mbc.day03.domain.ShoppingDTO;
import com.mbc.day03.service.ArtistService;
import com.mbc.day03.service.CategoryService;
import com.mbc.day03.service.GroupService;
import com.mbc.day03.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @Autowired
    GroupService groupService;

    @Autowired
    ShoppingService shoppingService;

    @Autowired
    CategoryService categoryService;


    // 아티스트 리스트 페이지 이동
    @GetMapping("/artistList")
    public String artist(Model model) {
        List<Map<String, Object>> list = artistService.getList();
        model.addAttribute("list", list);
        List<Map<String, Object>> Glist = groupService.getList();
        model.addAttribute("Glist", Glist);
        return "/artist/artistList";
    }

    // 아티스트 등록 페이지 이동
    @GetMapping("/register")
    public String registerForm(@RequestParam(required = false, defaultValue = "0") int groupNo, Model model) {
        List<Map<String, Object>> Glist = groupService.getList();
        model.addAttribute("Glist", Glist);
        model.addAttribute("groupNo", groupNo);
        return "/artist/register";
    }
    
    // 아티스트 등록 정보 백엔드 보내기
    @PostMapping("/register")
    public String artistRegister(@RequestParam("groupNo")int groupNo,
                                 @RequestParam("name")String name,
                                 @RequestParam("image")MultipartFile image,
                                 @RequestParam("birth")String birth) {

        String path = "C:/proj_files/images/";

        String filename_url = image.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + filename_url;
        System.out.println("filename = " + filename);
        String filepath = path + "\\" + filename;
        
        int n = artistService.PartistRegister(groupNo, name, filename, birth);

        File file = new File(filepath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(image.getBytes());
            bos.close();

        } catch (Exception e) {
            System.out.println("=======================Fail=======================");
            return "redirect:/artist/artistList";
        }

        return "redirect:/artist/artistList";
    }

    // 아티스트 삭제 페이지 이동
    @GetMapping("/delete")
    public String artistDelete(@RequestParam("no") int no){
        int n = artistService.artistDelete(no);
        return "redirect:/artist/artistList";
    }

    // 아티스트 수정 페이지 이동
    @GetMapping("/modify")
    public String artistModify(@RequestParam("no")int no, Model model){
        Map<String, Object> map = artistService.getListOne(no);
        model.addAttribute("map", map);
        List<Map<String, Object>> Glist = groupService.getList();
        model.addAttribute("Glist", Glist);
        return "/artist/modify";
    }

    // 아티스트 수정 정보 백엔드 보내기
    @PostMapping("/modify")
    public String artistModify(@RequestParam("no") int no,
                               @RequestParam("groupNo")int groupNo,
                               @RequestParam("name")String name,
                               @RequestParam("image")MultipartFile image,
                               @RequestParam("oldImage") String oldImage,
                               @RequestParam("birth")String birth) {

        String path = "C:/proj_files/images/";

        String filename_url = image.getOriginalFilename();
        String filename = "";
        String filepath = "";

        if(filename_url == null || filename_url.trim().equals("")){
            filename = oldImage;
            filepath = path + "\\" + oldImage;

            int n = artistService.PartistModify(no, groupNo, name, filename, birth);

            return "redirect:/artist/artistList";
        } else {
            filename = System.currentTimeMillis() + "_" + filename_url;
            filepath = path + "\\" + filename;

            int n = artistService.PartistModify(no, groupNo, name, filename, birth);

            File file = new File(filepath);

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(image.getBytes());
                bos.close();

            } catch (Exception e) {
                System.out.println("=======================Fail=======================");
                return "redirect:/artist/artistList";
            }

            return "redirect:/artist/artistList";

        }

    }

    // 아티스트 목록 선택 삭제 백엔드 보내기
    @PostMapping("/delete")
    public String artistDelete(@RequestParam("chkNo") List<String> chkList){
        if (chkList != null){
            int n = artistService.PartistDelete(chkList);
        }
        return "redirect:/artistList";
    }

    // 아티스트 검색정보 백엔드 보내기
    @PostMapping("/search")
    public String artistSearch(@RequestParam(value="name", defaultValue="all") String name,
                               @RequestParam("groupName") String groupName,
                               Model model){
        List<Map<String, Object>> list = artistService.artistSearch(name, groupName);
        model.addAttribute("list", list);
        return "/artist/artistList";
    }


    //아티스트 페이지에도 상품으로 넘어가는 작업 (9월 9일)
    @RequestMapping("/UprodView.do")
    public String UprodView(@RequestParam(value = "no", required = false, defaultValue = "0") int no, Model model) {
        if (no == 0) {
            return "redirect:/error"; // Redirect to an error page if No is invalid.
        }

        // Retrieve category list
        ArrayList<CategoryDTO> categoryList = (ArrayList<CategoryDTO>) categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // Retrieve product information
        ShoppingDTO pDto = shoppingService.getShopping((long) no);
        model.addAttribute("pDto", pDto);

        return "shopping/U_prodView";
    }

    //상품 그룹화 - 이렇게 했을 때, 로딩이 굉장히 느려짐 (9월 9일 메모)
//    @GetMapping("/shop")
//    public String showShopPage(Model model) {
//        // 카테고리 리스트를 가져와서 모델에 추가
//        List<CategoryDTO> categoryList = categoryService.categoryList();
//        model.addAttribute("categorylist", categoryList);
//
//        // 모든 상품 리스트를 가져옴
//        List<ShoppingDTO> shoppingList = shoppingService.shoppingList();
//
//        // 카테고리별로 상품을 그룹화
//        Map<Integer, List<ShoppingDTO>> map = categoryList.stream()
//                .collect(Collectors.toMap(
//                        CategoryDTO::getCnum, // 키: 카테고리 번호 (int)
//                        category -> shoppingList.stream()
//                                .filter(dto -> {
//                                    try {
//                                        // categoryNo를 Integer로 변환 후 비교
//                                        return Integer.parseInt(dto.getCategoryNo()) == category.getCnum();
//                                    } catch (NumberFormatException e) {
//                                        // 변환 실패 시 false 반환
//                                        return false;
//                                    }
//                                })
//                                .collect(Collectors.toList()) // 리스트로 수집
//                ));
//
//        // 카테고리 리스트와 그룹화된 맵을 모델에 추가
//        model.addAttribute("categorylist", categoryList);
//        model.addAttribute("map", map); // 카테고리별로 그룹화된 상품 목록
//
//        return "/artist/shop"; // 템플릿으로 이동
//    }

    //페이지 네이션 추가 9월 9일 금요일 - 그래서 페이지네이션 기능을 추가함
    @GetMapping("/shop")
    public String showShopPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model) { //한 페이지 당 8개 상품이 보이도록 설정

        // 카테고리 리스트를 가져와서 모델에 추가
        List<CategoryDTO> categoryList = categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // 모든 상품 리스트를 가져옴
        List<ShoppingDTO> allShoppingList = shoppingService.shoppingList();


        // 전체 상품에 대한 페이징 처리
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, allShoppingList.size());
        List<ShoppingDTO> paginatedList = allShoppingList.subList(startIndex, endIndex);

        // 카테고리별로 상품을 그룹화
        Map<Integer, List<ShoppingDTO>> categoryMap = paginatedList.stream()
                .collect(Collectors.groupingBy(dto -> {
                    try {
                        return Integer.parseInt(dto.getCategoryNo());
                    } catch (NumberFormatException e) {
                        return 0; // 기본 카테고리 또는 에러 처리
                    }
                }));

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) allShoppingList.size() / size);

        // 모델에 페이지네이션 정보 추가
        model.addAttribute("map", categoryMap);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);


        return "/artist/shop";
    }

    // 상품 검색 결과 처리 9월 10일 - 근데 바인딩이 안되어서 다시 봐야할 듯 함 ! -> 9월 11일 완료 view에서 artist 경로 부르는거 빼먹음
    @PostMapping("/shop/search")
    public String searchProductsPost(@RequestParam("name") String name, Model model) {
        List<ShoppingDTO> searchResults = shoppingService.searchProductsByName(name);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("searchTerm", name);
        return "artist/shopsearch";  // 'artist/' 접두사 추가
    }

    // 카테고리별 상품을 보여주는 메서드 추가 9월 16일
    @GetMapping("/category/{categoryNo}")
    public String showProductsByCategory(@PathVariable("categoryNo") String categoryNo, Model model) {
        // 카테고리 리스트 가져오기
        List<CategoryDTO> categoryList = categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // 선택된 카테고리의 상품 리스트 가져오기
        List<ShoppingDTO> productList = shoppingService.getProdByCategory(categoryNo);  // 변수명 productList로 변경
        model.addAttribute("productList", productList);  // 일관성 유지

        return "/shopping/category_products";  // 경로 일관성 유지
    }
}
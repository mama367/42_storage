package com.mbc.day03.controller;

import com.mbc.day03.domain.CategoryDTO;
import com.mbc.day03.domain.ShoppingDTO;
import com.mbc.day03.service.CategoryService;
import com.mbc.day03.service.GroupService;
import com.mbc.day03.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GroupService groupService;

    // 상품 리스트 띄우기
    @GetMapping("/shopping")
    public String productList(Model model) {
        //상품 리스트 가져오기
        ArrayList<ShoppingDTO> shoppingList = (ArrayList<ShoppingDTO>) shoppingService.shoppingList();
        model.addAttribute("list", shoppingList);

        List<Map<String, Object>> groupList = groupService.getList();
        model.addAttribute("groupList", groupList);

        return "shopping/prod_list";
    }

    // 상품 등록 폼 - GET 방식
    @GetMapping("/shopping/prodInput")
    public String productInputForm(Model model) {
        // 카테고리 리스트 뷰로 넘기기
        ArrayList<CategoryDTO> categoryList = (ArrayList<CategoryDTO>) categoryService.categoryList();
        model.addAttribute("categoryList", categoryList);

        List<Map<String, Object>> groupList = groupService.getList();
        model.addAttribute("groupList", groupList);



        return "shopping/prod_input";
    }

    // 상품 등록 - POST 방식
    @PostMapping("/shopping/register")
    public String insertShopping(MultipartHttpServletRequest mhr, Model model,
                                 HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        // 파일 저장 경로 설정 (수정된 부분)
        String savePath = "c:/proj_files/images";  // 절대 경로를 직접 사용

        System.out.println("savePath: " + savePath);

        // 1. DTO 객체 생성 및 설정
        ShoppingDTO shoppingDTO = new ShoppingDTO();

        // 2. 넘길 정보 얻어오기
        Enumeration<String> enu = mhr.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement();
            String paramValue = mhr.getParameter(paramName);

            // DTO에 값 설정
            switch (paramName) {
                case "name":
                    shoppingDTO.setName(paramValue);
                    break;
                case "price":
                    shoppingDTO.setPrice(Integer.parseInt(paramValue));
                    break;
                case "userNo":
                    shoppingDTO.setUserNo(Integer.parseInt(paramValue));
                    break;
                case "groupNo":
                    shoppingDTO.setGroupNo(Integer.parseInt(paramValue));
                    break;
//                case "Report":
//                    shoppingDTO.setReport(Integer.parseInt(paramValue));
//                    break;
//                case "Score":
//                    shoppingDTO.setScore(Integer.parseInt(paramValue));
//                    break;
                case "pcontent":
                    shoppingDTO.setPcontent(paramValue);
                    break;
                case "categoryNo":
                    shoppingDTO.setCategoryNo(paramValue);
                    break;
            }
        }

        Iterator<String> iter = mhr.getFileNames();
        while (iter.hasNext()) {
            String fileParamName = iter.next();
            // 3. 첨부파일 처리
            MultipartFile mFile = mhr.getFile(fileParamName);
            String originName = mFile.getOriginalFilename();

            if (originName != null && !originName.isEmpty()) {
                // 고유한 파일명 생성
                String uniqueFileName = System.currentTimeMillis() + "_" + originName;
                File uploadFile = new File(savePath + File.separator + uniqueFileName);

                // 파일 저장 경로 확인 및 생성
                File fileDir = new File(savePath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                // 실제 파일 업로드
                mFile.transferTo(uploadFile);

                // DTO에 저장할 이미지 파일명 설정
                shoppingDTO.setImage(uniqueFileName);
            }
        }

        // 서비스로 상품 정보 전달
        shoppingService.insertShopping(shoppingDTO);
        redirectAttributes.addFlashAttribute("msg", "상품 등록 완료!");

        return "redirect:/shopping";
    }

    // 상품 삭제하기
    @PostMapping("/shopping/deleteShopping")
    public String deleteProduct(@RequestParam("no") int no, @RequestParam("image") String PImage,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) {

        // 파일 저장 경로 (수정된 부분)
        String savePath = "c:/proj_files/images";  // 절대 경로를 직접 사용

        File delFile = null;
        if (PImage != null && !PImage.isEmpty()) {
            delFile = new File(savePath + File.separator + PImage);
            if (delFile.exists()) {
                if (delFile.delete()) {
                    System.out.println("이미지 파일 삭제 완료!!");
                } else {
                    System.out.println("이미지 파일 삭제 실패!!");
                }
            } else {
                System.out.println("이미지 파일이 존재하지 않음!!");
            }
        }

        // 데이터베이스에서 상품 삭제
        shoppingService.deleteShopping(no);

        // 리다이렉트 시 메시지 추가
        redirectAttributes.addFlashAttribute("msg", "상품 삭제 완료!");
        return "redirect:/shopping";
    }

    ////////////////////////////////////////////////////////////
    // 상품 정보 상세보기 및 수정 페이지
    @GetMapping("/shopping/prodUpdate")
    public String getProductForUpdate(@RequestParam("no") int no, Model model) {

        // 카테고리 리스트를 뷰로 넘겨주기
        List<CategoryDTO> categoryList = categoryService.categoryList();
        model.addAttribute("categoryList", categoryList);

        List<Map<String, Object>> groupList = groupService.getList();
        model.addAttribute("groupList", groupList);

        // 상품 정보 가져오기
        ShoppingDTO dto = shoppingService.getShopping((long) no);
        model.addAttribute("pDto", dto);

        return "shopping/prod_update";
    }

    // 상품 수정 - POST 방식
    @PostMapping("/shopping/updateProduct")
    public String updateProduct(MultipartHttpServletRequest mhr,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) throws IOException {

        // 파일 저장 경로 설정 (수정된 부분)
        String savePath = "c:/proj_files/images";  // 절대 경로를 직접 사용

        System.out.println("savePath: " + savePath);

        ShoppingDTO shoppingDTO = new ShoppingDTO();

        Enumeration<String> enu = mhr.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement();
            String paramValue = mhr.getParameter(paramName);

            switch (paramName) {
                case "no":
                    shoppingDTO.setNo((long) Integer.parseInt(paramValue));
                    break;
                case "name":
                    shoppingDTO.setName(paramValue);
                    break;
                case "price":
                    shoppingDTO.setPrice(Integer.parseInt(paramValue));
                    break;
                case "username":
                    shoppingDTO.setUserNo(Integer.parseInt(paramValue));
                    break;
                case "groupNo":
                    shoppingDTO.setGroupNo(Integer.parseInt(paramValue));
                    break;
                case "report":
                    shoppingDTO.setReport(Integer.parseInt(paramValue));
                    break;
                case "score":
                    shoppingDTO.setScore(Integer.parseInt(paramValue));
                    break;
                case "pcontent":
                    shoppingDTO.setPcontent(paramValue);
                    break;
                case "categoryNo":
                    shoppingDTO.setCategoryNo(paramValue);
                    break;
                case "imageOld":
                    shoppingDTO.setImage(paramValue);
                    break;
            }
        }

        Iterator<String> iter = mhr.getFileNames();
        while (iter.hasNext()) {
            String fileParamName = iter.next();
            MultipartFile mFile = mhr.getFile(fileParamName);
            String originName = mFile.getOriginalFilename();

            if (originName != null && !originName.isEmpty()) {
                String uniqueFileName = System.currentTimeMillis() + "_" + originName;
                File uploadFile = new File(savePath + File.separator + uniqueFileName);

                File fileDir = new File(savePath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                mFile.transferTo(uploadFile);
                shoppingDTO.setImage(uniqueFileName);
            }
        }

        shoppingService.updateShopping(shoppingDTO);
        redirectAttributes.addFlashAttribute("msg", "상품 수정 완료!");

        return "redirect:/shopping";
    }


///////////////////// 유저 아티스트 페이지에 보이게 하기
//여기서 부터는 유저가 보게될 파트

    // uleft 카테고리 리스트 띄우기
    @RequestMapping("/shopping/UcatList.do")
    public String categoryList(@RequestParam("cat_name") String cat_name,
                               @RequestParam("code") String code,
                               Model model) {
        ArrayList<CategoryDTO> categoryList = (ArrayList<CategoryDTO>) categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // 상품스펙(열거형 상수 - enum) 가져오기
        pcontent[] pdSpecs = pcontent.values(); // pcontent Enum 값을 배열로 가져옴
        model.addAttribute("pdSpecs", pdSpecs); // 바인딩

        ArrayList<ShoppingDTO> cList = (ArrayList<ShoppingDTO>) shoppingService.getProdByCategory(code);
        model.addAttribute("cList", cList);
        model.addAttribute("cat_name", cat_name);

        return "shopping/U_categoryList";
    }


    // uleft 카테고리 리스트 띄우기 - 이거 상세보기로 넘어감. 그룹 이름으로 바꾸면 좋을 것 같긴함
    @RequestMapping("/shopping/UprodView.do")
    public String UprodView(@RequestParam(value = "No", required = false, defaultValue = "0") int No, Model model) {
        if (No == 0) {
            return "redirect:/error"; // Redirect to an error page if No is invalid.
        }

        // Retrieve category list
        ArrayList<CategoryDTO> categoryList = (ArrayList<CategoryDTO>) categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // Retrieve product information
        ShoppingDTO pDto = shoppingService.getShopping((long) No);
        model.addAttribute("pDto", pDto);

        return "shopping/U_prodView";
    }


    //    // uleft 브랜드 리스트 띄우기(그룹 브랜드로 띄우기 염두)
    @RequestMapping("/shopping/pcontentList.do")
    public String pcontentList(String name, @RequestParam(value = "pcontent", required = false) String pcontentName, Model model) {
        // 카테고리 리스트 뷰에 넘겨주기
        ArrayList<CategoryDTO> categoryList = (ArrayList<CategoryDTO>) categoryService.categoryList();
        model.addAttribute("categorylist", categoryList);

        // 상품스펙(열거형 상수 - enum) 가져오기
        pcontent[] content = pcontent.values(); // Enum 값을 배열로 가져옴
        System.out.println("content = " + content);
        model.addAttribute("content", content); // 바인딩할 때 이름을 'content'로 설정

        // 스펙 클릭시 넘어감
        if (pcontentName != null && !pcontentName.isEmpty()) {
            try {
                pcontent specEnum = pcontent.valueOf(pcontentName);
                ArrayList<ShoppingDTO> sList = shoppingService.getProdByPcontent(specEnum.name());
                model.addAttribute("sList", sList);
                model.addAttribute("pcontent", specEnum);
                model.addAttribute("pcontentName", specEnum.getValue());
            } catch (IllegalArgumentException e) {
                // 잘못된 pcontentName 값이 들어오는 경우 로그를 남기거나 처리
                e.printStackTrace();
            }
        }

        return "shopping/pcontentList";
    }

    //판매자 상품 페이지 만들기 9월 11일, 9월 12일
    @GetMapping("/seller/details")
    public String getSellerDetails(@RequestParam("userno") int userNo, Model model) {
        // 해당 판매자의 상품 리스트를 가져오는 로직 추가
        List<ShoppingDTO> sellerProducts = shoppingService.getProductsByUser(userNo);
        model.addAttribute("sellerProducts", sellerProducts);

        // 사용자 번호를 모델에 추가
        model.addAttribute("userNo", userNo);
        return "shopping/sellerpage";  // 알맞은 페이지 경로로 변경
    }


}



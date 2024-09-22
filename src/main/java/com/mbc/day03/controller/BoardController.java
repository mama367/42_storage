package com.mbc.day03.controller;

import com.mbc.day03.domain.BoardDTO;
import com.mbc.day03.domain.PageDTO;
import com.mbc.day03.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/boardRegister.do")
    public String register(Model model) {
        return "board/register";
    }

    @GetMapping("/artistBoardRegister.do")
    public String register2(Model model, int btype, int gno) {
        model.addAttribute("btype", btype);
        model.addAttribute("gno", gno);

        return "board/register";
    }

    @PostMapping("/boardRegister.do")
    public String register(BoardDTO dto, @RequestParam("btype") int btype, Model model) {

//		for(int i=1; i<=123; i++) {
//			BoardDTO bDto = new BoardDTO();
//			bDto.setSubject(i + "번째 제목입니다~~~");
//			bDto.setContents(i + "번째 내용입니다~~~");
//			bDto.setWriter("아무개 " + (i%10));
//			service.register(bDto);
//		}
        model.addAttribute("btype", btype);

        boardService.register(dto);
        return "redirect:/artistBoard.do";
    }

    @PostMapping("/artistBoardRegister.do")
    public String register2(PageDTO pDto, BoardDTO dto, int btype, int gno, Model model, RedirectAttributes rttr) {

//		for(int i=1; i<=123; i++) {
//			BoardDTO bDto = new BoardDTO();
//			bDto.setSubject(i + "번째 제목입니다~~~");
//			bDto.setContents(i + "번째 내용입니다~~~");
//			bDto.setWriter("아무개 " + (i%10));
//			service.register(bDto);
//		}
        rttr.addAttribute("btype", btype);
        rttr.addAttribute("gno", gno);

        int viewPage = pDto.getViewPage();

        rttr.addAttribute("viewPage", viewPage);
        rttr.addAttribute("searchType", pDto.getSearchType());
        rttr.addAttribute("keyword", pDto.getKeyword());
        rttr.addAttribute("cntPerPage", pDto.getCntPerPage());

        boardService.register2(dto, btype, gno);
        return "redirect:/artistBoard.do";
    }

//	@GetMapping("/list.do")
//	public String list(Model model) {
//		List<BoardDTO> list = service.getList();
//		model.addAttribute("list", list);
//
//		return "board/boardList";
//	}

    // 게시글 리스트 페이징 처리
//	@GetMapping("/list.do")
////	public String list(@RequestParam(name="viewPage", defaultValue="1") int viewPage, Model model) {
//	public String list(PageDTO pDto, Model model) {
//		List<BoardDTO> list = service.getList(pDto);
//		model.addAttribute("list", list);
//
//		// serviceImpl에서 셋팅된 pDto
//		model.addAttribute("pDto", pDto);
//
//		return "board/boardList";
//	}

    @RequestMapping("/boardList.do")
    public String list(PageDTO pDto, Model model) {
        List<BoardDTO> list = boardService.getList(pDto);
        model.addAttribute("list", list);
        System.out.println("list = " + list);
        // serviceImpl에서 셋팅된 pDto
        model.addAttribute("pDto", pDto);

        return "board/boardList";
    }

    @RequestMapping("/artistBoard.do")
    public String artistBoard(PageDTO pDto, Model model, int btype, int gno) {
        List<BoardDTO> list = boardService.getListByArtist(pDto, btype, gno);
        model.addAttribute("btype", btype);
        model.addAttribute("gno", gno);
        model.addAttribute("list", list);
        System.out.println("list = " + list);
        // serviceImpl에서 셋팅된 pDto
        model.addAttribute("pDto", pDto);

        return "board/boardList";
    }

    // 글상세보기
    @GetMapping("/boardView.do")
    public String view(int bid, PageDTO pDto, Model model) {
        BoardDTO dto = boardService.view(bid, "v");
        model.addAttribute("dto", dto);
        model.addAttribute("pDto", pDto);

        return "board/view";
    }

    // 글상세보기
    @GetMapping("/ArtistBoardView.do")
    public String ArtistBoardView(int bid, PageDTO pDto, Model model, int btype, int gno) {
        BoardDTO dto = boardService.view(bid, "v");
        model.addAttribute("dto", dto);
        model.addAttribute("pDto", pDto);

        return "board/view";
    }

    // 수정페이지 이동
    @GetMapping("/boardModify.do")
    public String modifyForm(PageDTO pDto, int bid, Model model) {
        System.out.println("=================");
        System.out.println("vp : " + pDto.getViewPage());
        BoardDTO dto = boardService.view(bid, "m");
        model.addAttribute("dto", dto);
        model.addAttribute("pDto", pDto);
        System.out.println("vp2 : " + pDto.getViewPage());

        return "board/modify";
    }

    @PostMapping("/boardModify.do")
    public String modify(PageDTO pDto, BoardDTO dto, RedirectAttributes rttr) {
        boardService.modify(dto);

        int viewPage = pDto.getViewPage();
        System.out.println("vp ======> " +viewPage);
        rttr.addAttribute("viewPage", viewPage);
        rttr.addAttribute("searchType", pDto.getSearchType());
        rttr.addAttribute("keyword", pDto.getKeyword());
        rttr.addAttribute("cntPerPage", pDto.getCntPerPage());

        return "redirect:boardList.do";
    }

    // 게시글 삭제
    @GetMapping("/boardRemove.do")
    public String remove(PageDTO pDto, int bid, RedirectAttributes rttr, int btype, int gno) {
        boardService.remove(bid);
        rttr.addAttribute("btype", btype);
        rttr.addAttribute("gno", gno);

        //** 게시판 검색 뒤 삭제 시 오류 발생 해결을 위한 코드 추가
        rttr.addAttribute("viewPage", pDto.getViewPage());
        rttr.addAttribute("searchType", pDto.getSearchType());
        rttr.addAttribute("keyword", pDto.getKeyword());
        rttr.addAttribute("cntPerPage", pDto.getCntPerPage());
        // redirect 시 model에 바인딩된 값은 queryString으로 전달된다.
        // PageDTO 객체는 queryString으로 전달될 수 없음(int, String은 가능)
        return "redirect:artistBoard.do";
    }

}

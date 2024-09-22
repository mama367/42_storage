package com.mbc.day03.controller;

import com.mbc.day03.domain.Reply2DTO;
import com.mbc.day03.domain.ReplyPageDTO;
import com.mbc.day03.service.Reply2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply2")
public class Reply2Controller {
    @Autowired
    private Reply2Service service;

    // 게시글에 대한 댓글 리스트-- 페이징처리 없을때
//   @GetMapping("/list/{pid}")
//   public List<ReplyDTO> getList(@PathVariable("pid") int pid){
//      return service.getList(pid);
//   }

    // 게시글에 대한 댓글 리스트-- 페이징 처리
    @GetMapping("/list/{pid}/{viewPage}")
    public ReplyPageDTO getList(@PathVariable("pid") int pid
            , @PathVariable("viewPage") int vp){
        System.out.println("pid = " + pid);
        ReplyPageDTO rPageDTO =service.getList(pid, vp);
        System.out.println("rPageDTO = " + rPageDTO);

        // 댓글 리스트와 paging처리를 위한 값들을 리턴
        return rPageDTO;
    }

    // 댓글 조회
    @GetMapping("/{rno}")
    public Reply2DTO read(@PathVariable("rno") int rno) {
        System.out.println("rno ====>" + rno);
        return service.read(rno);
    }

    // 댓글 등록
    @PostMapping("/new")
    // JSON형태의 문자열을 자바객체(ReplyDTO)로 변환하기 위해서는 @RequestBody가 필요
    // @ResponseBody 필요 없음
    public String create(@RequestBody Reply2DTO rDto) {
        int n = service.register(rDto);
        return n == 1 ? "success" : "fail";
    }

    // 댓글 삭제
    @DeleteMapping("/{rno}")
    public String remove(@PathVariable("rno") int rno) {
        int n = service.remove(rno);
        return n == 1 ? "success" : "fail";
    }

    // 댓글 수정
    @PutMapping("/{rno}")
    public String modify(@PathVariable("rno") int rno,
                         @RequestBody Reply2DTO rDto) {

        rDto.setRno(rno);
        int n = service.modify(rDto);
        return n == 1 ? "success" : "fail";
    }
}

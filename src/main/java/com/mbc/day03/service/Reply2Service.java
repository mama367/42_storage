package com.mbc.day03.service;

import com.mbc.day03.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Reply2Service {
    @Autowired
    private Reply2DAO reply2DAO;

    //댓글 추가
    @Transactional
    public int register(Reply2DTO rDto) {
        int n = reply2DAO.insert(rDto);
        int r = reply2DAO.rating(rDto.getPid());
        reply2DAO.prod_rating(r, rDto.getPid());
        return n;
    }

    // 댓글 삭제
    @Transactional
    public int remove(int rno) {
        Reply2DTO rDto= reply2DAO.select(rno);
        int n = reply2DAO.delete(rno);
        return n;
    }

    public int modify(Reply2DTO rDto) {
        return reply2DAO.update(rDto);
    }

    public Reply2DTO read(int rno) {
        // TODO Auto-generated method stub
        return reply2DAO.select(rno);
    }

//   public List<ReplyDTO> getList(int pid, int vp) {
    public ReplyPageDTO getList(int pid, int vp) {

        // bid 해당하는 전체 댓글 수를 구하기
        int replyCnt = reply2DAO.replyCnt(pid);


        ReplyPageDTO rPageDTO= new ReplyPageDTO();
        // viewPage 바뀔때 마다 새롭게 셋팅
        rPageDTO.setViewPage(vp);

        rPageDTO.setValue(replyCnt);

        List<Reply2DTO> rList= reply2DAO.getListByPid(pid
                ,rPageDTO.getStartIndex()
                , rPageDTO.getCntPerPage());

//      return rList;

        // 조회된 댓글 리스트를 DTO에 셋팅을 해줌
        rPageDTO.setReply2List(rList);

        return rPageDTO;
    }
}

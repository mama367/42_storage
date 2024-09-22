package com.mbc.day03.service;

import com.mbc.day03.domain.BoardDAO;
import com.mbc.day03.domain.ReplyDAO;
import com.mbc.day03.domain.ReplyDTO;
import com.mbc.day03.domain.ReplyPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private BoardDAO boardDAO;

    //댓글 추가
    @Transactional
    public int register(ReplyDTO rDto) {
        boardDAO.updateReplyCnt(rDto.getBid(), 1);
        int n = replyDAO.insert(rDto);
        return n;
    }

    // 댓글 삭제
    @Transactional
    public int remove(int rno) {
        ReplyDTO rDto= replyDAO.select(rno);
        boardDAO.updateReplyCnt(rDto.getBid(), -1);
        int n = replyDAO.delete(rno);
        return n;
    }

    public int modify(ReplyDTO rDto) {
        return replyDAO.update(rDto);
    }

    public ReplyDTO read(int rno) {
        // TODO Auto-generated method stub
        return replyDAO.select(rno);
    }

//   public List<ReplyDTO> getList(int bid, int vp) {
    public ReplyPageDTO getList(int bid, int vp) {

        // bid 해당하는 전체 댓글 수를 구하기
        int replyCnt = replyDAO.replyCnt(bid);


        ReplyPageDTO rPageDTO= new ReplyPageDTO();
        // viewPage 바뀔때 마다 새롭게 셋팅
        rPageDTO.setViewPage(vp);

        rPageDTO.setValue(replyCnt);

        List<ReplyDTO> rList= replyDAO.getListByBid(bid
                ,rPageDTO.getStartIndex()
                , rPageDTO.getCntPerPage());

//      return rList;

        // 조회된 댓글 리스트를 DTO에 셋팅을 해줌
        rPageDTO.setReplyList(rList);

        return rPageDTO;
    }
}

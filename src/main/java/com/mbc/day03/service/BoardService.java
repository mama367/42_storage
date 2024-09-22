package com.mbc.day03.service;

import com.mbc.day03.domain.BoardDAO;
import com.mbc.day03.domain.BoardDTO;
import com.mbc.day03.domain.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardDAO boardDAO;

    public void register(BoardDTO dto) {
        boardDAO.insert(dto);

    }

    public void register2(BoardDTO dto, int btype, int gno) {
        boardDAO.insert2(dto, btype, gno);

    }

//	public List<BoardDTO> getList() {
    @Transactional
    public List<BoardDTO> getList(PageDTO pDto) {

//		int totalCnt = mapper.totalCnt();

        // 검색이 되는 경우
        // xml에 searchType, keyword를 전달해야
        // WHERE절 처리가 됨, 즉, pDTO를 전달해야 함
        int totalCnt = boardDAO.totalCnt(pDto);

        // setValue호출시 startIndex 셋팅됨
        pDto.setValue(totalCnt, pDto.getCntPerPage());

//		List<BoardDTO> list = mapper.getList();
//		return list;

        return boardDAO.getList(pDto);
    }

    @Transactional
    public List<BoardDTO> getListByArtist(PageDTO pDto, int btype, int gno) {
        // 검색이 되는 경우
        // xml에 searchType, keyword를 전달해야
        // WHERE절 처리가 됨, 즉, pDTO를 전달해야 함
        int totalCnt = boardDAO.totalCnt2(pDto);//** 게시판이 artistboard로 수정된 뒤 총 글 개수를 받아오기 위한 코드

        // setValue호출시 startIndex 셋팅됨
        pDto.setValue(totalCnt, pDto.getCntPerPage());

        return boardDAO.getListByArtist(pDto, btype, gno);
    }

    @Transactional
    public List<BoardDTO> getTopListByArtist(PageDTO pDto, int btype, int gno) {

        return boardDAO.getTopListByArtist(pDto, btype, gno); //** 아티스트 페이지에서 글 3개씩만 받아올 수 있도록 수정됨
    }

    public BoardDTO view(int bid, String mode) {

        if(mode.equals("v")) {
            // 조회수 추가
            boardDAO.hitAdd(bid);
        }

        return boardDAO.view(bid);
    }

    public BoardDTO artistBoardView(int bid, String mode, int gno, int btype) {

        if(mode.equals("v")) {
            // 조회수 추가
            boardDAO.hitAdd(bid);
        }

        return boardDAO.view(bid);
    }

    public void modify(BoardDTO dto) {
        boardDAO.update(dto);
    }

    public void remove(int bid) {
        boardDAO.delete(bid);

    }
}

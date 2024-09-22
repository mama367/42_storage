package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDAO {
    // 게시글 등록
    void insert(BoardDTO dto);

    void insert2(BoardDTO dto, int btype, int gno);

    // 게시글 리스트
//	List<BoardDTO> getList();
    List<BoardDTO> getList(PageDTO pDto);

    List<BoardDTO> getListByArtist(PageDTO pDto, int btype, int gno);

    List<BoardDTO> getTopListByArtist(PageDTO pDto, int btype, int gno);

    // 글 상세보기
    BoardDTO view(int bid);

    BoardDTO artistBoardView(int bid);

    // 글 수정하기
    void update(BoardDTO dto);

    // 글 삭제하기
    void delete(int bid);

    // 조회수 추가하기
    void hitAdd(int bid);

    // 전체 게시글 수
//	int totalCnt();
    // 검색이 있을 경우
    int totalCnt(PageDTO pDto);

    int totalCnt2(PageDTO pDto);

    void updateReplyCnt(@Param("bid") int bid, @Param("n") int n);

}

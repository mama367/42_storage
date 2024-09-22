package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyDAO {
    List<ReplyDTO> getListByBid(@Param("bid") int bid, @Param("startIndex") int si,
                                @Param("cntPerPage") int cp);

    ReplyDTO select(int rno);

    int update(ReplyDTO rDto);

    int insert(ReplyDTO rDto);

    int delete(int rno);

    int replyCnt(int bid);
}

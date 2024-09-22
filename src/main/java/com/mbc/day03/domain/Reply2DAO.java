package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface Reply2DAO {
    List<Reply2DTO> getListByPid(@Param("pid") int pid, @Param("startIndex") int si,
                                @Param("cntPerPage") int cp);

    Reply2DTO select(int rno);

    int update(Reply2DTO rDto);

    int insert(Reply2DTO rDto);

    int delete(int rno);

    int replyCnt(int pid);

    int rating(int pid);

    int prod_rating(double score, int pid);
}

package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArtistDAO {
    List<Map<String, Object>> getList();

    int artistRegister(int groupNo,
                       String name,
                       String image,
                       String birth);

    int artistDelete(int no);

    Map<String, Object> getListOne(int no);

    List<Map<String, Object>> groupArtist(int no);

    int artistModify(int no, int groupNo, String name, String image, String birth);

    int PartistDelete(List<String> chkList);

    List<Map<String, Object>> artistSearch(String name, String groupName);
}

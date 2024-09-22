package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupDAO {
    List<Map<String, Object>> getList();

    int groupRegister(int no,
                      String name,
                      String debut,
                      String info,
                      String image,
                      String message);
    int groupDelete(int no);

    Map<String, Object> getListOne(int no);

    int groupModify(int no, String name, String debut, String info, String image, String message);

    int PgroupDelete(List<String> chkList);

    List<Map<String, Object>> groupSearch(String groupName, String name);
}
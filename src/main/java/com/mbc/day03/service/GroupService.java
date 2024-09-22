package com.mbc.day03.service;

import com.mbc.day03.domain.GroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GroupService {
    @Autowired
    GroupDAO groupDAO;

    public List<Map<String, Object>> getList(){
        return groupDAO.getList();
    }

    public int PgroupRegister(int no, String name, String debut, String info, String image, String message){
        return groupDAO.groupRegister(no, name, debut, info, image, message);
    }

    public int groupDelete(int no){
        return groupDAO.groupDelete(no);
    }

    public Map<String, Object> getListOne(int no){
        return groupDAO.getListOne(no);
    }

    public int PgroupModify(int no, String name, String debut, String info, String image, String message){
        return groupDAO.groupModify(no, name, debut, info, image, message);
    }

    public int PgroupDelete(List<String> chkList){
        return groupDAO.PgroupDelete(chkList);
    }

    public List<Map<String, Object>> groupSearch(String groupName, String name){
        return groupDAO.groupSearch(groupName, name);
    }
}
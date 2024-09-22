package com.mbc.day03.service;

import com.mbc.day03.domain.ArtistDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ArtistService {
    @Autowired
    ArtistDAO artistDAO;

    public List<Map<String, Object>> getList() {
        return artistDAO.getList();
    }

    public int PartistRegister(int groupNo, String name, String image, String birth){
        return artistDAO.artistRegister(groupNo, name, image, birth);
    }

    public int artistDelete(int no){
        return artistDAO.artistDelete(no);
    }

    public Map<String, Object> getListOne(int no){
        return artistDAO.getListOne(no);
    }

    public List<Map<String, Object>> groupArtist(int no){
        return artistDAO.groupArtist(no);
    }

    public int PartistModify(int no, int groupNo, String name, String image, String birth){
        return artistDAO.artistModify(no, groupNo, name, image, birth);
    }

    public int PartistDelete(List<String> chkList){
        return artistDAO.PartistDelete(chkList);
    }

    public List<Map<String, Object>> artistSearch(String name, String groupName){
        return artistDAO.artistSearch(name, groupName);
    }
}
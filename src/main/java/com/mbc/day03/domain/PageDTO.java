package com.mbc.day03.domain;

import lombok.Data;

@Data
public class PageDTO {
    private int viewPage = 1; // 현재페이지
    private int blockSize = 5;
    private int blockNum; // 블럭의 위치
    private int blockStart; //블럭의 시작값
    private int blockEnd; //블럭의 마지막값
    private int nextPage;
    private int prevPage;
    private int totalPage; // 전체 페이지수

    // 전체 게시글 수
    private int totalCnt;

    // 페이지당 게시글 수
    private int cntPerPage = 10;

    // 각페이지별 시작값
    private int startIndex;

    ///////////////// search ////////////////////
    private String searchType;
    private String keyword;
    private int btype;
    private int gno;

    // 멤버변수 값 설정
    public void setValue(int totalCnt, int cntPerPage) {
        // 전체 게시글수 재설정
        this.totalCnt = totalCnt;

        // 전체페이지수
        this.totalPage = (int)Math.ceil((double)totalCnt/cntPerPage);
        //0, 10, 20,...
        this.startIndex =(viewPage - 1)*cntPerPage;

        // 블럭위치(0부터 시작)
        this.blockNum = (viewPage-1)/blockSize;

        // 블럭의 시작값(1,6,11,....)
        this.blockStart = (blockSize*blockNum) + 1;

        // 블럭의 마지막값(5, 10, 15, 20, .....)
        this.blockEnd = blockStart + (blockSize - 1);
        if(blockEnd > totalPage) blockEnd = totalPage;

        // 이전페이지
        this.prevPage = blockStart - 1;

        // 다음페이지
        this.nextPage = blockEnd + 1;
    }
}

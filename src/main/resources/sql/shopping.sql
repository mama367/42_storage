-- -- 쇼핑 테이블
-- drop table product2;
--
-- create table product2(
--                          No int primary key auto_increment,    -- 상품번호(1)
--                          Nm varchar(50) not null,    -- 상품명(2)
--                          Pr int default 0, -- 가격(3)
--                          PImage varchar(300),        -- 상품이미지(4)
--                          SNo  int default 0,    -- 판매자 번호(5)
--                          GroupNo   int default 0,    -- 그룹번호(6)
--                          CategoryNo	 varchar(20) not null, -- 카테고리 번호(7)
--                          Report   int default 0,    -- 신고횟수(8)
--                          Score   int default 0,    -- 별점(9)
--                          pcontent varchar(300)    -- 상품상세(10+)
-- );

-- 쇼핑 테이블
drop table product2;

create table product2(
                         No int primary key auto_increment,    -- 상품번호(1)
                         Nm varchar(50) not null,    -- 상품명(2)
                         Pr int default 0, -- 가격(3)
                         PImage varchar(300),        -- 상품이미지(4)
                         SNo  int default 0,    -- 판매자 번호(5)
                         GroupNo   int default 0,    -- 그룹번호(6)
                         CategoryNo varchar(20) not null, -- 카테고리 번호(7)
                         Report   int default 0,    -- 신고횟수(8)
                         Score   int default 0,    -- 별점(9)
                         pcontent varchar(300)    -- 상품상세(10+)
);

drop table User;
create table User(
                     no int auto_increment primary key,		-- 번호
                     name varchar(20) not null,				-- 이름
                     id varchar(20) not null,				-- 아이디
                     password varchar(200) not null,			-- 비밀번호
                     email varchar(20) not null,				-- 이메일
                     tel varchar(20) not null,				-- 전화번호
                     address varchar(100) not null,			-- 주소
                     zip varchar(20) not null,				-- 우편번호
                     date datetime default now(),			-- 가입일
                     privileges int not null,				-- 관리자 구분 번호(0: 최고관리자, 1: 판매자, 2: 일반유저)
                     birth varchar(20) not null,					-- 생년월일
                     report int								-- 신고횟수
);
select * from User;
insert into User (name, id, password, email, tel, address, zip,  privileges, birth, report)
values
    ('홍길동', 'test', 'test', 'test@gmail.com', '01012341234', '서울시 강서구', '26403', 0, '000109', 0);
drop table artist;
create table artist(
                       no int primary key auto_increment,
                       groupNo int not null,
                       groupName varchar(20) not null,
                       name varchar(20) not null,
                       image varchar(50) not null,
                       birth varchar (20) not null,
                       foreign key (groupNo) references `group`(no),
                       foreign key (groupName) references `group`(name)
);
select * from artist;
insert into artist
values
    (1, 100, '방탄소년단', 'RM', 'RM이미지', '19940912'),
    (2, 100, '방탄소년단', '진', '진이미지', '19921204'),
    (3, 100, '방탄소년단', '슈가', '슈가이미지', '19930309'),
    (4, 100, '방탄소년단', '제이홉', '제이홉이미지', '19940218'),
    (5, 100, '방탄소년단', '지민', '지민이미지', '19951013'),
    (6, 100, '방탄소년단', '뷔', '뷔이미지', '19951230'),
    (7, 100, '방탄소년단', '정국', '정국이미지', '19970901');
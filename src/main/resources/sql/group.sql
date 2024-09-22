drop table `group`;
create table `group`(
                        no int unique not null,
                        name varchar(20) unique not null,
                        debut datetime not null,
                        info varchar(200) not null,
                        image varchar(50) not null,
                        message varchar(50) not null
);
select * from `group`;
insert into `group`
values (100, '방탄소년단', 20130613, '방탄 info', '방탄이미지', '방탄 한마디'),
       (200, '세븐틴', 20150526, '세븐틴 info', '세븐틴이미지', '세븐틴 한마디');
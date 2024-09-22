drop table product;
create table product(
                        no int primary key auto_increment,
                        Name varchar(20) not null,
                        price int not null,
                        image varchar(50) not null,
                        userNo int not null,
                        groupNo int not null,
                        categoryNo int not null,
                        report int not null,
                        score int,
                        foreign key (userNo) references user(no),
                        foreign key (groupNo) references `group`(no),
                        foreign key (categoryNo) references category(no)
);
select * from product;
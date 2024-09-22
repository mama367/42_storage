create table boot_product(
        pno int not null auto_increment,
        pname varchar(50) not null,
        kind varchar(50) not null,
        price int not null default 0,
        in_date datetime not null default current_timestamp(),
        up_date datetime not null default current_timestamp(),
        primary key(pno)
);

desc boot_product;

insert into boot_product (pname, kind, price)
values
    ('스프링부트','도서', 30000),
    ('파이썬','도서', 20000),
    ('냉장고','생활가전', 2000000);

select * from boot_product;

create table order_detail
(
    oid     int auto_increment
        primary key,
    pno     int                                not null,
    pname   varchar(30)                        null,
    price   int                                not null,
    id      varchar(30)                        not null,
    name    varchar(30)                        not null,
    in_date datetime default CURRENT_TIMESTAMP not null
);
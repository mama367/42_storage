drop table category;
create table category(
                         no int unique not null,
                         groupNo int not null,
                         category int not null
);
select * from category;
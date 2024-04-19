-- drop table cart;

create table cart(
    id INT AUTO_INCREMENT PRIMARY KEY,
    userid  varchar(80) not null,
    itemid varchar(80),
    productid varchar(80),
    listprice decimal(10,2) null,
    number int not null,
    -- 数目
    itemattr varchar(80),
    name varchar(80)
    -- productname : "Amazon Parrot"
);

-- 日志
-- create table log(
--     username varchar(10),
--     logtime varchar(80),
--     action varchar(80),
--     request varchar(80)
-- );

drop database mypetstore;
create database mypetstore;
use mypetstore;

-- 表头

-- drop index productCat;
-- drop index productName;
-- drop index itemProd;

-- drop table lineitem;
-- drop table orderstatus;
-- drop table orders;
-- drop table bannerdata;
-- drop table profile;
-- drop table signon;
-- drop table inventory;
-- drop table item;
-- drop table product;
-- drop table account;
-- drop table category;
-- drop table supplier;
-- drop table sequence;

create table supplier (
                          suppid int not null,
                          name varchar(80) null,
                          status varchar(2) not null,
                          addr1 varchar(80) null,
                          addr2 varchar(80) null,
                          city varchar(80) null,
                          state varchar(80) null,
                          zip varchar(5) null,
                          phone varchar(80) null,
                          constraint pk_supplier primary key (suppid)
);

create table signon (
                        username varchar(25) not null,
                        password varchar(25)  not null,
                        constraint pk_signon primary key (username)
);

create table account (
                         userid varchar(80) not null,
                         email varchar(80) not null,
                         firstname varchar(80) not null,
                         lastname varchar(80) not null,
                         status varchar(2)  null,
                         addr1 varchar(80) not null,
                         addr2 varchar(40) null,
                         city varchar(80) not  null,
                         state varchar(80) not null,
                         zip varchar(20) not null,
                         country varchar(20) not null,
                         phone varchar(80) not null,
                         constraint pk_account primary key (userid)
);

create table profile (
                         userid varchar(80) not null,
                         langpref varchar(80) not null,
                         favcategory varchar(30),
                         mylistopt int,
                         banneropt int,
                         constraint pk_profile primary key (userid)
);

create table bannerdata (
                            favcategory varchar(80) not null,
                            bannername varchar(255)  null,
                            constraint pk_bannerdata primary key (favcategory)
);

create table orders (
                        orderid int auto_increment not null,
                        userid varchar(80) not null,
                        orderdate date not null,
                        shipaddr1 varchar(80) not null,
                        shipaddr2 varchar(80) null,
                        shipcity varchar(80) not null,
                        shipstate varchar(80) not null,
                        shipzip varchar(20) not null,
                        shipcountry varchar(20) not null,
                        billaddr1 varchar(80) not null,
                        billaddr2 varchar(80)  null,
                        billcity varchar(80) not null,
                        billstate varchar(80) not null,
                        billzip varchar(20) not null,
                        billcountry varchar(20) not null,
                        courier varchar(80) not null,
                        totalprice decimal(10,2) not null,
                        billtofirstname varchar(80) not null,
                        billtolastname varchar(80) not null,
                        shiptofirstname varchar(80) not null,
                        shiptolastname varchar(80) not null,
                        creditcard varchar(80) not null,
                        exprdate varchar(7) not null,
                        cardtype varchar(80) not null,
                        locale varchar(80) not null,
                        constraint pk_orders primary key (orderid)
);

create table orderstatus (
                             orderid int not null,
                             linenum int not null,
    -- 这个玩意是表示待处理,处理中 的各种状态, 结果是个int?
--       Pending（待处理）：通常用 0 表示。
--       Processing（处理中）：通常用 1 表示。
--       Shipped（已发货）：通常用 2 表示。
--       Delivered（已送达）：通常用 3 表示。
--       Cancelled（已取消）：通常用 -1 表示
                             timestamp date not null,
                             status varchar(2) not null,
                             constraint pk_orderstatus primary key (orderid, linenum)
);

create table lineitem (
                          orderid int not null,
                          linenum int not null,
                          itemid varchar(10) not null,
                          quantity int not null,
                          unitprice decimal(10,2) not null,
                          constraint pk_lineitem primary key (orderid, linenum)
);

create table category (
                          catid varchar(10) not null,
                          name varchar(80) null,
                          descn varchar(255) null,
                          constraint pk_category primary key (catid)
);

create table product (
                         productid varchar(10) not null,
                         category varchar(10) not null,
                         name varchar(80) null,
                         descn varchar(255) null,
                         img varchar(100) null,
                         constraint pk_product primary key (productid),
                         constraint fk_product_1 foreign key (category)
                             references category (catid)
);

create index productCat on product (category);
create index productName on product (name);


create table inventory (
                           itemid varchar(10) not null,
                           qty int not null,
                           constraint pk_inventory primary key (itemid)
);

CREATE TABLE sequence
(
    name               varchar(30)  not null,
    nextid             int          not null,
    constraint pk_sequence primary key (name)
);

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

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                          `status` int NOT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

create table item (
                      itemid varchar(10) not null,
                      productid varchar(10) not null,
                      listprice decimal(10,2) null,
                      unitcost decimal(10,2) null,
                      supplier int null,
                      status varchar(2) null,
                      attr1 varchar(80) null,
                      attr2 varchar(80) null,
                      attr3 varchar(80) null,
                      attr4 varchar(80) null,
                      attr5 varchar(80) null,
                      constraint pk_item primary key (itemid),
                      constraint fk_item_1 foreign key (productid)
                          references product (productid),
                      constraint fk_item_2 foreign key (supplier)
                          references supplier (suppid),
                      CONSTRAINT fk_item_3 FOREIGN KEY (`supplier`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

create index itemProd on item (productid);
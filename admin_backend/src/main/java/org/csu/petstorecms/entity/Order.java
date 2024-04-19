package org.csu.petstorecms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @TableId("orderid")
    private int orderid;
    @TableField("userid")
    private String userid;
    @TableField("orderdate")
    private Date orderdate;
    @TableField("shipaddr1")
    private String shipfromadd1;
    @TableField("shipaddr2")
    private String shipfromadd2;
    @TableField("shipcity")
    private String shipfromcity;
    @TableField("shipstate")
    private String shipfromstate;
    @TableField("shipzip")
    private String shipfromzip;
    @TableField("shipcountry")
    private String shipcountry;
    @TableField("billaddr1")
    private String billaddr1;
    @TableField("billaddr2")
    private String billaddr2;
    @TableField("billcity")
    private String billcity;
    @TableField("billstate")
    private String billstate;
    @TableField("billzip")
    private String billzip;
    @TableField("billcountry")
    private String billcountry;
    @TableField("courier")
    private String courier;
    @TableField("billtofirstname")
    private String billtofirstname;
    @TableField("billtolastname")
    private String billtolastname;
    @TableField("shiptofirstname")
    private String shiptofirstname;
    @TableField("shiptolastname")
    private String shiptolastname;
    @TableField("creditcard")
    private String creditcard;
    @TableField("exprdate")
    private String exprdate;
    @TableField("cardtype")
    private String cardtype;
    @TableField("locale")
    private String locale;
    @TableField("totalprice")
    private double totalprice;
}

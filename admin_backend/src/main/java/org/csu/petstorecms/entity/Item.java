package org.csu.petstorecms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("item")
public class Item {
    @TableId("itemid")
    private String itemid;
    @TableField("productid")
    private String productid;
    @TableField("listprice")
    private double listprice;
    @TableField("unitcost")
    private double unitcost;
    @TableField("supplier")
    private int supplier;
    @TableField("status")
    private String status;
    @TableField("attr1")
    private String attr1;
    @TableField("attr2")
    private String attr2;
    @TableField("attr3")
    private String attr3;
    @TableField("attr4")
    private String attr4;
    @TableField("attr5")
    private String attr5;
}

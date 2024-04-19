package org.csu.petstorecms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId("productid")
    private String productid;
    @TableField("category")
    private String category;
    @TableField("name")
    private String name;
    @TableField("descn")
    private String descn;
    @TableField("img")
    private String img;
}

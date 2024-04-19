package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("cart")
public class Cart {
    @TableField("id")
    private int id;
    @TableField("userid")
    private String userId;
    @TableField("itemid")
    private String itemId;
    @TableField("productid")
    private String productId;
    @TableField("productimage")
    private String productImage;
    @TableField("listprice")
    private BigDecimal listPrice;
    private int number;
    @TableField("itemattr")
    private String itemAttribute;
    @TableField("name")
    private String productName;
}

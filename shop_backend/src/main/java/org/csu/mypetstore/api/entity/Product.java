package org.csu.mypetstore.api.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId(value = "productid")
    private String productId; // product_id
    @TableField("category")
    private String categoryId;
    private String name;
    @TableField("descn")
    private String description;
    @TableField("img")
    private String image;
}

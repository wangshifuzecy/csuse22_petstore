package org.csu.mypetstore.api.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVO {
    //item table field
    private String itemId;
    private String productId;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private int supplierId;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String status;

    //product
    private String categoryId;
    private String productName;
    private String productDescription;
    private String productImage;

    //item inventory
    private int quantity;
}

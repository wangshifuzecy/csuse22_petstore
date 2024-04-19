package org.csu.petstorecms.vo;

import lombok.Data;

@Data
public class ItemVO {

    private String itemid;
    private String productid;
    private double listprice;
    private double unitcost;
    private int supplier;
    private String status;
    private String attr1;

    //category
    private String categoryid;

    //product
    private String img;
    private String descn;
    //inventory
    private int quantity;

}

package org.csu.petstorecms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inventory")
public class Inventory {
    @TableId("itemid")
    private String itemid;
    @TableField("qty")
    private int qty;
}

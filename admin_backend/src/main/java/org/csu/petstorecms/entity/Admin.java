package org.csu.petstorecms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class Admin {
    @TableId(type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    @TableField(value = "real_name")
    private String realName;
    private String phone;
    private String address;
    private int status;  //0表示商家，1表示超级管理员，可以管理商家
}

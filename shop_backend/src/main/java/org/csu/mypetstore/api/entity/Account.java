package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @TableId("userid")
    private String username;
    private String email = "acid@yourdomain.com";
    @TableField("firstname")
    private String firstName = "wang";
    @TableField("lastname")
    private String lastName = "jj";
    private String status = "ok";
    @TableField("addr1")
    private String address1 = "changsha";
    @TableField("addr2")
    private String address2 = "jiangxi";
    private String city = "changsha";
    private String state = "hunan";
    private String zip = "223234";
    private String country = "china";
    private String phone = "13030579570";
}

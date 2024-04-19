package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @TableId("userid")
    private String userId;
    @TableField("favcategory")
    private String favouriteCategory = "DOGS";
    @TableField("langpref")
    private String languagePreference = "english";
    @TableField("mylistopt")
    private int listOption = 1;
    @TableField("banneropt")
    private int bannerOption = 1;
}

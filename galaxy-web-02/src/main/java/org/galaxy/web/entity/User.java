package org.galaxy.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 性别 0女1男
     */
    @TableField("sex")
    private Short sex;


    /**
     * 体重
     */
    @TableField("weight")
    private Double weight;

    /**
     * 身高
     */
    @TableField("height")
    private Double height;

}

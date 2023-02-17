package org.galaxy.common.serialization.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class User implements Serializable {

    private String name;//姓名
    private Integer age;//年龄
    private Short sex;//性别，1代表男，0代表女
    private Double weight;//体重，单位为kg
    private Double height;//身高，单位为m
}

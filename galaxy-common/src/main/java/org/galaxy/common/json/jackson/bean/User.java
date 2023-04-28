package org.galaxy.common.json.jackson.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private Integer age;

    private Boolean sex;

    private Double height;



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Cat {

        private String color;

        private Double weight;
    }
}

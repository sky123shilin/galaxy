package org.galaxy.common.enums;

/**
 * 枚举也可以实现某个接口，在每个枚举对象实现接口中的方法
 */
public enum OperationEnum implements Operation{

    PLUS("+"){
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },

    MINUS("-"){
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },

    MULTI("x"){
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },

    DIVIDE("/"){
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    }
    ;

    private final String name;

    OperationEnum(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

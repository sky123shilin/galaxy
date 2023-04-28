package org.galaxy.util.collection;

import java.io.Serializable;

/**
 * 二元组
 * @param <S>
 * @param <T>
 */
public class Tuple2<S,T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final S one;
    private final T two;

    private Tuple2(S var1, T var2){
        this.one = var1;
        this.two = var2;
    }

    public static <S,T> Tuple2<S,T> of(S var1,T var2){
        return new Tuple2<>(var1,var2);
    }

    public S getOne(){
        return this.one;
    }

    public T getTwo(){
        return this.two;
    }

    @Override
    public String toString() {
        return "Tuple2[ one = " + one + ", two = " + two  + " ]";
    }
}

package org.galaxy.util.collection;

import java.io.Serializable;

/**
 * 三元祖
 * @param <R>
 * @param <S>
 * @param <T>
 */
public class Tuple3<R,S,T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final R one;
    private final S two;
    private final T three;

    Tuple3(R one, S two, T three){
        this.one = one;
        this.two = two;
        this.three = three;
    }

    static <R,S,T> Tuple3<R,S,T> of(R var1,S var2, T var3){
        return new Tuple3<>(var1,var2,var3);
    }

    public R getOne(){
        return one;
    }

    public S getTwo(){
        return two;
    }

    public T getThree(){
        return three;
    }

    @Override
    public String toString() {
        return "Tuple3[ one = " + one + ", two = " + two + ", three = " + three + "]";
    }
}

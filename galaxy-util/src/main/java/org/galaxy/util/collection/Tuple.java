package org.galaxy.util.collection;

import java.io.Serializable;

/**
 * java中的元祖Tuple实现
 * @param <T>
 */
public class Tuple<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final T[] args;

    @SafeVarargs
    public static <T> Tuple<T> of(T ... args){
        return new Tuple<>(args);
    }

    private Tuple(T[] args){
        this.args = args;
    }

    public T get(int index){
        if(index < 0 || args == null || args.length < 1 || index > args.length - 1){
            return null;
        }
        return args[index];
    }

    public T[] toArray(){
        return args;
    }

//    public static void main(String[] args) {
//        Tuple<String> tuple = Tuple.of("1","2","3");
//        System.out.println(tuple.get(0));
//        System.out.println(tuple.get(1));
//        System.out.println(tuple.get(2));
//    }
}

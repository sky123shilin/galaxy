package org.galaxy.common.serviceloader;

public interface Ordered {

    int MIN_ORDER = Integer.MIN_VALUE;
    int MAX_ORDER = Integer.MAX_VALUE;

    int getOrder();

}

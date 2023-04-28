package org.galaxy.id.generator;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用Mongo的ObjectId来生成Id
 * 借助于mongo-java-driver中的ObjectId来生成Id
 * 或者自己copy过来直接使用
 */
@Component
@Slf4j
public class MongoObjectIdKeyGenerator implements KeyGenerator {

    @Override
    public Comparable<?> generateId() {
        return ObjectId.get().toHexString();
    }

    @Override
    public String supportType() {
        return Type.Mongo_ObjectId.name();
    }

}

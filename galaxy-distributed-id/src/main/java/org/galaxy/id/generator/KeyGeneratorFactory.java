//package org.galaxy.common.keygen;
//
//import lombok.Getter;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// *
// * 两个维度
// * 1.业务线维度
// * 2.KeyGenerator类型
// *
// * 做到：同样的业务线下共用同一个KeyGenerator,按照KeyGenerator提供的类型生成ID
// *
// * 分布式ID的工厂类
// */
//public class KeyGeneratorFactory {
//
//    private static final Map<TypeEnum,KeyGenerator> keyGeneratorMap = new ConcurrentHashMap<>(2);
//
//    static{
//        keyGeneratorMap.put(TypeEnum.Increment,new IncrementKeyGenerator());
//        keyGeneratorMap.put(TypeEnum.Snowflake,new SnowflakeKeyGenerator());
//        keyGeneratorMap.put(TypeEnum.Leaf,new LeafKeyGenerator());
//        keyGeneratorMap.put(TypeEnum.Redis,new RedisKeyGenerator());
//        keyGeneratorMap.put(TypeEnum.UUID,new UUIDKeyGenerator());
//        keyGeneratorMap.put(TypeEnum.Batch_DB,new BatchDBKeyGenerator());
//    }
//
//    /**
//     * 这里逻辑好好想一想
//     */
//    public static KeyGenerator create(BizTypeEnum bizType, TypeEnum type){
//        KeyGenerator keyGenerator = keyGeneratorMap.getOrDefault(type,null);
//        return null;
//    }
//
//    /**
//     * keyGenerator类型
//     */
//    public enum TypeEnum{
//        Increment,
//        Snowflake,
//        Leaf,
//        Redis,
//        Batch_DB,
//        UUID
//    }
//
//    /**
//     * 业务线类型
//     */
//    public enum BizTypeEnum{
//
//        ORDER_SN("交易单号","order_sn"),
//        PAY_SN("支付单号","pay_sn"),
//        REFUND_SN("退款单号","refund_sn")
//        ;
//        @Getter
//        private final String bizType;
//        @Getter
//        private final String prefix;
//
//        BizTypeEnum(String bizType, String prefix){
//            this.bizType = bizType;
//            this.prefix = prefix;
//        }
//    }
//
//}

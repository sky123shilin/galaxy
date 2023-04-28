package org.galaxy.id.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 一次按需批量生成多个ID，每次生成都需要访问数据库，将数据库修改为最大的ID值，并在内存中记录当前值及最大值。
 * 数据库如果是单点的，存在单点故障，且服务重启的话会造成ID不连续问题
 */
@Component
@Slf4j
public class MysqlBatchKeyGenerator implements KeyGenerator {

    @Override
    public Comparable<?> generateId() {
        return null;
    }

    @Override
    public String supportType() {
        return Type.Mysql_Batch.name();
    }
}

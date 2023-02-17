package org.galaxy.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.galaxy.web.constants.Constants;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * Mysql数据源配置
 */
@Configuration
@MapperScan(basePackages = "org.galaxy.web.mapper", sqlSessionFactoryRef = "mysqlSessionFactory", sqlSessionTemplateRef = "mysqlSessionTemplate")
public class MysqlDataSourceConfig {

    @Bean(name = "mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "mysqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("mysqlDataSource") DataSource datasource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(metaObjectHandler());
        bean.setDataSource(datasource);
        bean.setGlobalConfig(globalConfig);
        //mybatis扫描xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        //分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        bean.setPlugins(interceptor);
        return bean.getObject();
    }

    @Bean("mysqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mysqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                // 创建时间
                this.strictInsertFill(metaObject, "createdDate", LocalDateTime.class, LocalDateTime.now());
                // 创建人
                Object createBy = getFieldValByName("createdBy", metaObject);
                if(createBy == null){
                    this.strictInsertFill(metaObject, "createdBy", String.class, Constants.DEFAULT_USER);
                }
                //更新时间
                this.strictUpdateFill(metaObject, "lastModifiedDate", LocalDateTime.class, LocalDateTime.now());
                // 更新人
                Object lastModifiedBy = getFieldValByName("lastModifiedBy", metaObject);
                if(lastModifiedBy == null){
                    this.strictInsertFill(metaObject, "lastModifiedBy", String.class, Constants.DEFAULT_USER);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                //更新时间
                this.strictUpdateFill(metaObject, "lastModifiedDate", LocalDateTime.class, LocalDateTime.now());
                // 更新人
                Object lastModifiedBy = getFieldValByName("lastModifiedBy",metaObject);
                if(lastModifiedBy == null){
                    this.strictInsertFill(metaObject, "lastModifiedBy", String.class, Constants.DEFAULT_USER);
                }
            }
        };
    }
}

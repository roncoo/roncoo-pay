package com.roncoo.pay.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class DruidDataConfig {

    @Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.initialSize}")
    private Integer initialSize;
    @Value("${jdbc.minIdle}")
    private Integer minIdle;
    @Value("${jdbc.maxActive}")
    private Integer maxActive;
    @Value("${jdbc.maxWait}")
    private Integer maxWait;

    @Primary
    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "clone")
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        //基本属性driverClassName、 url、user、password
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //初始化时建立物理连接的个数，缺省值为0
        dataSource.setInitialSize(initialSize);
        //最小连接池数量
        dataSource.setMinIdle(minIdle);
        //最大连接池数量，缺省值为8
        dataSource.setMaxActive(maxActive);
        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁
        dataSource.setMaxWait(maxWait);

        return dataSource;
    }

}

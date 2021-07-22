package com.zhousj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhousj
 * @date 2020-12-8
 */
@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.zhousj.*.dao"})
@EnableScheduling
@PropertySource("classpath:config.properties")
public class RightsorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RightsorderApplication.class, args);
    }
}

package com.zhousj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liangkb
 * @Title: Swagger2Config
 * @ProjectName settlecenter
 * @Description: Swagger配置类
 * @date 2018/8/219:31
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 添加摘要信息
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：openAPI")
                        .description("描述：参数配置服务")
                        .contact(new Contact("zhousj", null, null))
                        .version("版本号:1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhousj"))
                .paths(PathSelectors.any())
                .build();
    }

}
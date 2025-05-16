package com.tensua.blogservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author zhooke
 * @since 2024/2/14 11:13
 **/
//@EnableFeignClients(basePackageClasses = {UserFeignClient.class})
@SpringBootApplication
// @EnableDiscoveryClient
@EntityScan("com.tensua.blogservice.*.entity")
@MapperScan({"com.tensua.blogservice.operator.*.mapper"})
public class BlogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);
    }

}

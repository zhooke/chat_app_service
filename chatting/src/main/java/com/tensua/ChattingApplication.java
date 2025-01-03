package com.tensua;

import com.tensua.api.UserFeignClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author:70968 Date:2021-10-23 11:21
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {UserFeignClient.class})
@EntityScan("com.tensua.*.entity")
@MapperScan("com.tensua.operator.chat.mapper")
@SpringBootApplication
public class ChattingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChattingApplication.class, args);
    }
}

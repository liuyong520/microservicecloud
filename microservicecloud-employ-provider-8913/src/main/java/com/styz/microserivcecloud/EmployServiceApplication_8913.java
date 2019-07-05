package com.styz.microserivcecloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <b>package:com.com.styz.com.com.styz.com.com.styz.com.com.styz.microserivcecloud</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 17:56</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@SpringBootApplication
@MapperScan("com.styz.microservicecloud.mapper")
@EnableEurekaClient
public class EmployServiceApplication_8913 {
    public static void main(String[] args) {
        SpringApplication.run(EmployServiceApplication_8913.class,args);
    }
}

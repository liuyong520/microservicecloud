package com.styz.microserivcecloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@MapperScan("com.com.styz.microservicecloud.mapper")
public class EmployServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployServiceApplication.class);
    }
}

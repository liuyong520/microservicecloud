package com.styz.microservice;

import com.styz.microservice.robbin.CountRobbinRule;
import com.styz.rules.MyRules;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * <b>package:com.com.styz.com.com.styz.com.styz.com.styz.com.styz.microservice</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 20:26</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@SpringBootApplication
@EnableDiscoveryClient
//指定某个服务用某种负载均衡算法
@RibbonClient(name = "EMPLOYSERVICE",configuration = MyRules.class)
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class,args);
    }
}

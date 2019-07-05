package com.styz.rules;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.styz.microservice.robbin.CountRobbinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <b>package:com.com.styz.rules</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-05 15:11</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@Configuration
public class MyRules {
    @Bean
    @Primary
    public IRule getRobbinRule(){
        return new RandomRule();
    }
}

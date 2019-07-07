package com.styz.microservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.styz.microservice.robbin.CountRobbinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <b>package:com.com.styz.com.com.styz.com.styz.com.styz.com.styz.microservice.config</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 20:29</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@Configuration
public class ConsummerConfigBean {
    /**
     * @LoadBalanced 注解会使用robbin负载均横算法。默认是轮训，如需修改得显示声明对应的算法。
     * 如果没有声明，默认就是轮训
     * robbin 提供7种轮训算法，我们可以手动扩展实现负载均衡算法。
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

//    @Bean
//    public IRule getRobbinRule(){
//        return new RandomRule();
//    }
//    @Bean
//    public IRule getRobbinRule(){
//        return new CountRobbinRule();
//    }
}

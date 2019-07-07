package com.styz.microservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.styz.microservicecloud.entity.Employ;
import com.styz.microservicecloud.service.EmployClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>package:com.com.styz.com.com.styz.com.styz.com.styz.com.styz.com.styz.microservice.controller</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 20:31</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@RestController
@RequestMapping("/comsummer")
public class EmployConsumerController {
    @Autowired
    private EmployClientService clientService;

    @RequestMapping("/getById/{id}")
    @HystrixCommand(fallbackMethod = "processEmployException")
    public Employ getEmployById(@PathVariable("id") long id){
        Employ employ = clientService.getEmployById(id);
        if(null == employ){
            throw new RuntimeException("未找到对应ID的员工！");
        }
        return employ;
    }

    /**
     * 这个方法可以写在服务端，也可以写在消费端，如果异常导致的最好写在服务端，如果非异常，可以实现在消费端
     * 方法写在服务端叫熔断，写在消费端叫降级。
     * HystrixCommand 注解能实现的熔断，但是缺点非常明显，容易导致方法膨胀：也就是每个方法都需要写一个异常方法
     * 方法会非常多，不便与维护。
     *
     * @param id
     * @return
     */
    public Employ processEmployException(@PathVariable("id") long id){
        Employ employ = new Employ();
        employ.setId(id);
        employ.setName("未找到对应ID的员工!");
        return employ;
    }
    @RequestMapping("/getAllEmployes")
    public List<Employ> getAllEmployes(){
        return clientService.getAllEmployes();
    }
}

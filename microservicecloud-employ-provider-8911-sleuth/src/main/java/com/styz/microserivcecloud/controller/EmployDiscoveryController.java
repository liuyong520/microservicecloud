package com.styz.microserivcecloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>package:com.com.styz.com.com.styz.com.com.styz.com.styz.microserivcecloud.controller</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-05 09:50</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):添加服务发现,主要供消费端使用</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@RestController
public class EmployDiscoveryController {
    @Autowired
    private DiscoveryClient client;
    @RequestMapping(value = "/employ/discovery",method = RequestMethod.GET)
    public Object discovery() {
        List<String> services = client.getServices();
        System.out.println("************" + services);
        List<ServiceInstance> instances = client.getInstances("EMPLOYSERVICE");
        for(ServiceInstance instance : instances){
            System.out.println(instance.getServiceId()+"\t" + instance.getHost() +"\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.client;
    }
}
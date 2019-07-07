package com.styz.microservice.controller;

import com.styz.microservicecloud.entity.Employ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <b>package:com.com.styz.com.com.styz.com.styz.com.styz.com.styz.microservice.controller</b>
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
//    public static final String EMPLOY_PROVIDE_URL="http://localhost:8999/employ";
    public static final String EMPLOY_PROVIDE_URL="http://EMPLOYSERVICE/employ";
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getById/{id}")
    public Employ getEmployById(@PathVariable("id") long id){
        return restTemplate.postForObject(EMPLOY_PROVIDE_URL+"/getEmployById/"+id,null,Employ.class);
    }
    @RequestMapping("/getAllEmployes")
    public List<Employ> getAllEmployes(){
        return restTemplate.postForObject(EMPLOY_PROVIDE_URL+"/getAllEmployes",null,List.class);
    }
}

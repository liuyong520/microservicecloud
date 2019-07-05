package com.styz.microserivcecloud.controller;

import com.styz.microserivcecloud.service.EmployService;
import com.styz.microservicecloud.entity.Employ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>package:com.com.styz.com.com.styz.com.com.styz.com.com.styz.microserivcecloud.controller</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 18:14</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@RestController
@RequestMapping("/employ")
public class EmployController {
    @Autowired
    private EmployService employService;

    @RequestMapping("/getAllEmployes")
    public List<Employ> getAllEmployes(){
        //手动添加异常代码测试服务降级
        //也可以开启服务，然后突然关掉服务进行测试
//        int i = 0;
//        int i1 = 10 / i;
        return employService.getAllEmployes();
    }
    @RequestMapping("/getEmployById/{id}")
    public Employ getEmployById(@PathVariable("id")long id){
        return employService.getEmployById(id);
    }
}

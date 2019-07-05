package com.styz.microservicecloud.service;

import com.styz.microservicecloud.entity.Employ;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <b>package:com.com.styz.microservicecloud.service</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-05 15:36</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):声明Feign接口</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */

/**
 * 服务降级处理：
 * 1.添加fallbackFactory 或者Fallback类
 * 2.开启服务降级：
 * feign:
 *   hystrix:
 *     enabled: true
 * 注意fallbackFactory类或者Fallback类要能被扫描到。否则不会生效。
 */
@FeignClient(value = "EMPLOYSERVICE",fallbackFactory = EmployClientServiceFallbackFactory.class)
public interface EmployClientService {
    //注意此时的url是服务端的全路径，不是客户端哦
    @RequestMapping(value = "/employ/getAllEmployes",method = RequestMethod.POST)
    public List<Employ> getAllEmployes();

    @RequestMapping(value = "/employ/getEmployById/{id}",method = RequestMethod.POST)
    public Employ getEmployById(@PathVariable("id")long id);

}

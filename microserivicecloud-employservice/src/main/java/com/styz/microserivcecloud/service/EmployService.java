package com.styz.microserivcecloud.service;

import com.styz.microservicecloud.entity.Employ;
import com.styz.microservicecloud.mapper.EmployMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b>package:com.com.styz.com.com.styz.com.com.styz.com.com.styz.microserivcecloud.service</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-04 18:17</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
public interface EmployService {
    /**
     * 获取所有员工
     * @return
     */
    public List<Employ> getAllEmployes();

    /**
     * 根据Id获取指定员工
     * @param id
     * @return
     */
    public Employ getEmployById(long id);
}

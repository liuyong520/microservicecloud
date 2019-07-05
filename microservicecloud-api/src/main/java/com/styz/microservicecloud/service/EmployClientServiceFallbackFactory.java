package com.styz.microservicecloud.service;

import com.styz.microservicecloud.entity.Employ;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>package:com.styz.microservicecloud.service</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-05 17:16</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
@Component
public class EmployClientServiceFallbackFactory implements InitializingBean,FallbackFactory<EmployClientService> {

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public static class EmployClientServiceFallback implements EmployClientService{

        @Override
        public List<Employ> getAllEmployes() {
            ArrayList<Employ> employs = new ArrayList<>();
            employs.add(new Employ().setName("********暂停服务*****"));
            System.out.println("*************暂停服务****************");
            return employs;
        }

        @Override
        public Employ getEmployById(long id) {
            return new Employ(id,"该ID不存在",0L,"");
        }
    }

    @Override
    public EmployClientService create(Throwable throwable) {
        return new EmployClientServiceFallback();
    }
}

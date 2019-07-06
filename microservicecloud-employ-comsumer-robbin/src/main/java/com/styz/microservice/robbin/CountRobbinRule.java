package com.styz.microservice.robbin;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b>package:com.com.styz.com.styz.com.styz.com.styz.microservice.robbin</b>
 * <b>project(项目):microservicecloud</b>
 * <b>class(类)${CLASS_NAME}</b>
 * <b>creat date(创建时间):2019-07-05 14:39</b>
 * <b>author(作者):</b>xxydliuyss</br>
 * <b>note(备注)):</b>
 * If you want to change the file header,please modify zhe File and Code Templates.
 */
public class CountRobbinRule implements IRule {
    private ILoadBalancer loadBalancer;
    private AtomicInteger count = new AtomicInteger(0);
    private AtomicInteger serverIndex = new AtomicInteger(0);
    @Override
    public Server choose(Object o) {
        List<Server> allServers = loadBalancer.getAllServers();
        while (true){
            if(count.get() > 5){
                if(serverIndex.incrementAndGet() > allServers.size()){
                    serverIndex.set(serverIndex.get()%allServers.size());
                }
                count.set(0);
            }else {
                count.incrementAndGet();
                return allServers.get(serverIndex.get());
            }
        }
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        loadBalancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return loadBalancer;
    }
}

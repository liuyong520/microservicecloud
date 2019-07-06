# microservicecloud
Microservice based on Springcloud，follwer me to build a microservice instance step by step 

# 插件推荐
在搭建项目之前先推荐两款很好用的IDEA插件，至于插件做什么用的,自己百度吧！
1.MyBatisCodeHelper-Pro

2.lombok 

# 建立父工程Microservicecloud
点击 File——>new——>Project 新建maven工程项目
修改pom.xml文件
```xml
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.30</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
                <version>2.0.3.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.security</groupId>
                <artifactId>spring-security-test</artifactId>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.1.0</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>repository.springframework.maven.release</id>
            <name>Spring Framework Maven Release Repository</name>
            <url>http://maven.springframework.org/milestone/</url>
        </repository>
        <repository>
            <id>org.springframework</id>
            <url> http://maven.springframework.org/snapshot</url>
        </repository>
        <repository>
            <id>spring-milestone</id>
            <name>Spring Maven MILESTONE Repository</name>
            <url>http://repo.spring.io/libs-milestone</url>
        </repository>
        <repository>
            <id>spring-release</id>
            <name>Spring Maven RELEASE Repository</name>
            <url>http://repo.spring.io/libs-release</url>
        </repository>
    </repositories>
```
## 搭建Employ员工服务
### 创建数据库
1.执行microserivcecloud-database中的Employ.sql文件创建员工服务需要用到的表
当然也可以自己考出来执行
```sql
use db;
SET STORAGE_ENGINE = InnoDB;

DROP TABLE IF EXISTS `employ`;
CREATE TABLE `employ` (
  `id` bigint(20) NOT NULL COMMENT '员工编号',
  `name` varchar(48) NOT NULL COMMENT '名字',
  `departmentId` bigint(10) NOT NULL COMMENT '部门编号',
  `datasourceId` varchar(64) NOT NULL COMMENT '数据存储位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into employ(id,name,departmentId,datasourceId) values(1,"zhansan",1,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(2,"lisi",2,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(3,"wangwu",3,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(4,"maliu",4,DATABASE());
```

2.创建springboot+mybatis整合项目
2.1创建microservicecloud-api 子模块项目用于存放公共的接口和实体
修改pom.xml文件
```xml
 <!--添加lombok依赖-->
 <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
2.2利用MyBatisCodeHelper-Pro生成mybatis的mapper接口和数据库实体到microservicecloud-api中
生成结果如下
```
com.styz.microservicecloud.entity.Employ #实体
com.styz.microservicecloud.mapper.EmployMapper #mapper接口
microservicecloud-api/src/main/resources/mapperxml/EmployMapper.xml #mapper.xml sql 文件
```
然后执行mvn install操作
2.3创建microservicecloud-employservice 模块
修改pom文件
```xml
<dependencies>
    <dependency>
        <groupId>com.com.styz.microservicecloud</groupId>
        <artifactId>microservicecloud-api</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.1</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.0</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>
```
创建com.styz.microservicecloud包新建EmployServiceApplication启动类
```java
@SpringBootApplication
@MapperScan("com.com.styz.microservicecloud.mapper")
public class EmployServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployServiceApplication.class);
    }
}
```
然后创建com.styz.microserivcecloud.service.EmployService接口以及其实现类
```java
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
```
```java
@Service
public class EmployServiceImpl implements EmployService {
    @Autowired
    private EmployMapper employMapper;

    public List<Employ> getAllEmployes() {
        return employMapper.listAll();
    }

    public Employ getEmployById(long id) {
        return employMapper.selectByPrimaryKey(id);
    }
}
```
然后创建com.styz.microserivcecloud.controller.EmployController
```java
@RestController
@RequestMapping("/employ")
public class EmployController {
    @Autowired
    private EmployService employService;

    @RequestMapping("/getAllEmployes")
    public List<Employ> getAllEmployes(){
        return employService.getAllEmployes();
    }
    @RequestMapping("/getEmployById/{id}")
    public Employ getEmployById(@PathVariable("id")long id){
        return employService.getEmployById(id);
    }
}
```
添加配置文件
```yaml
#应用程序使用的端口
server:
  port: 8999
#mybatis相关配置
mybatis:
  config-location: classpath:mybatis/mybatis.cfg.xml
  type-alias-package: com.com.styz.microservicecloud.entity
  mapper-locations:
    - classpath*:mapperxml/*Mapper.xml
#数据源配置
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://k8s-n1:3307/db
    username: root
    password: 123456
    dbcp2:
      min-idle: 5
      initial-size: 5
      max-idle: 10
      max-total: 20
      max-wait-millis: 10
# 服务名称
  application:
    name: EmployService
    
```

mybatis/mybatis.cfg.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--二级缓存开启-->
        <setting name="cacheEnabled" value="true"/>
    </settings>
</configuration>
```
**注意：@MapperScan("com.com.styz.microservicecloud.mapper")**必须扫描到API模块里的mapper接口

2.5 自嗨一下
访问http://localhost:8999/employ/getAllEmployes 查看一下是否正常返回
自此单机服务已经整合完毕

## 创建消费者服务microservicecloud-employconsummer
1.新建模块microservicecloud-employconsummer修改pom文件
```xml
<dependencies>
    <dependency>
        <groupId>com.com.styz.microservicecloud</groupId>
        <artifactId>microservicecloud-api</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
```
2.添加配置application.yml
```yaml
server:
  port: 8111
spring:
  application:
    name: EmployComsummer
  devtools:
    restart:
      enabled: true
```
3.新建config配置类com.styz.microservice.config.ConsummerConfigBean
```java
@Configuration
public class ConsummerConfigBean {
   /**
    * RestTemplate 封装了httpclient的实现能够简化我们对请求的访问
    * @return 
    */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
} 
```
4.创建消费者controller com.styz.microservice.controller.EmployConsumerController
```java
@RestController
@RequestMapping("/comsummer")
public class EmployConsumerController {
    //服务提供方的地址：http://localhost:8999/employ/getAllEmployes
    public static final String EMPLOY_PROVIDE_URL="http://localhost:8999/employ";
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
```
5.新建消费者启动类：com.styz.microservice.EmployComsumerApplication
```java
@SpringBootApplication
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class);
    }
}
```
至此单体消费者实例已经完成。自嗨一下：访问http://localhost:8111/consummer/getById/1 看看能不能调用到服务端
提供的服务

# Eureka注册中心搭建
Eureka主要的功能就是服务注册与发现。是Netflix公司下开源一款产品，功能比较单一
基于AP原则实现。cs架构模式实现,想要详细了Eureka可以百度。
# 单机模式
## 创建microservicecloud-registry-8211 模块
1.修改pom文件
```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <finalName>${project.name}</finalName>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
2.创建com.styz.microservice.RegistryApplication_8211 启动类
```java
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication_8211 {
    public static void main(String[] args) {
        SpringApplication.run(RegistryApplication_8211.class,args);
    }
}
```
3.配置文件
```yaml

spring:
  application:
    name: registry-8211

eureka:
  instance:
    #euraka 服务器实例名称
    #主机名字需要修改host文件
    hostname: registry8211
  client:
    #false表示不向注册中心注册自己
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #单机配置 http://${eureka.instance.hostname}:${server.port}/eureka
      #集群环境搭建:http://registry8212:8212/eureka/,http://registry8213:8213/eureka/
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
server:
  port: 8211
```
单机注册中心已经完成 访问 http://registry8211:8211/ 就会出现注册中心的界面
# 集群搭建
## 修改microservicecloud-registry-8211 模块
1.只需要修改bootstrap.yml中的
```yaml
 defaultZone: http://registry8212:8212/eureka/,http://registry8213:8213/eureka/
```
## 拷贝microservicecloud-registry-8211 两份
microservicecloud-registry-8212 microservicecloud-registry-8213
同理修改
```yaml
 
spring:
  application:
    name: registry-8212

eureka:
  instance:
    #euraka 服务器实例名称
    hostname: registry8212
  client:
    #false表示不向注册中心注册自己
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #单机配置 http://${eureka.instance.hostname}:${server.port}/eureka
      #集群环境搭建
      defaultZone: http://registry8211:8211/eureka/,http://registry8213:8213/eureka/
server:
  port: 8212
```
```yaml

spring:
  application:
    name: registry-8213

eureka:
  instance:
    #euraka 服务器实例名称
    hostname: registry8213
  client:
    #false表示不向注册中心注册自己
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      #单机配置 http://${eureka.instance.hostname}:${server.port}/eureka
      #集群环境搭建
      defaultZone: http://registry8211:8211/eureka/,http://registry8212:8212/eureka/
server:
  port: 8213
```
修改hosts文件
```
127.0.0.1  registry8213 registry8212 registry8211
```
至此集群模式搭建完毕，可以分别访问 http://registry8211:8211/ http://registry8212:8212/ http://registry8213:8213/
出现Eureka的界面表示成功。

# 注册服务到注册中心
## 新建microservicecloud-employproviderwithregistry
拷贝microserivicecloud-employservice中的代码和配置文件到microservicecloud-employproviderwithregistry 模块中
修改pom文件
```xml
<!--添加注册中心依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!--添加springboot监控依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

添加bootstrap.yml文件

```yaml
#服务名称
spring:
  application:
    name: EmployService
eureka:
  client:
    service-url:
      #单机配置：http://localhost:8211/eureka
      defaultZone: http://registry8212:8212/eureka/,http://registry8213:8213/eureka/,http://registry8211:8211/eureka/
  # 修改注册服务的名字
  instance:
    instance-id: EmployService-8999
    # 防问路径可以显示IP地址
    prefer-ip-address: true
# 添加服务Info信息
info:
  app.name: EmployService-8999
  app.describution: 员工服务
  company.name: com.com.styz
  author.name: liuy
  build.artifactId: $project.artifactId$
  build.version: $project.version$
```
修改启动类com.styz.microserivcecloud.EmployServiceApplication
添加@EnableEurekaClient注解
```java
@SpringBootApplication
@MapperScan("com.com.styz.microservicecloud.mapper")
@EnableEurekaClient
public class EmployServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployServiceApplication.class,args);
    }
}
```
然后可以查看注册中心是否有服务注册进去了。

# 消费者调用
修改microservicecloud-employconsummer中的com.styz.microservice.controller.EmployConsumerController
中的
```java
// EMPLOYSERVICE为服务名称
public static final String EMPLOY_PROVIDE_URL="http://EMPLOYSERVICE/employ";
```
修改pom添加eureka客户端依赖
```xml
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
修改启动类添加服务发现注解@EnableDiscoveryClient
```java
@SpringBootApplication
@EnableDiscoveryClient
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class,args);
    }
}
```
修改yml文件
```yaml
eureka:
  client:
    service-url:
      #单机配置：http://localhost:8211/eureka
      defaultZone: http://registry8212:8212/eureka/,http://registry8213:8213/eureka/,http://registry8211:8211/eureka/
    register-with-eureka: false
```
此时访问消费者 就能够完成消费者 调用 服务端服务 的调用了

# 服务集群搭建

为了介绍下面的resttemplate+ribbon 和feign负载均衡，需要将服务作集群搭建。
基本架构是，每个服务访问自己独立的数据库，数据上构造一定的差异，这样方便我们测试负载均衡。
## 执行Employ-cluster.sql文件中的sql 
创建三个不同的库，同样的表。然后插入不同的数据。
## 拷贝microservicecloud-employproviderwithregistry 三份
分别为microservicecloud-employ-provider-8911 microservicecloud-employ-provider-8912 microservicecloud-employ-provider-8913
具体修改内容见代码。
## 创建microservicecloud-employ-comsumer-robbin 模块
1.修改pom文件
```xml
<!-- 添加 ribbon依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
修改启动类
```java
@SpringBootApplication
@EnableDiscoveryClient
//指定某个服务用某种负载均衡算法
@RibbonClient(name = "EMPLOYSERVICE",configuration = MyRules.class)
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class,args);
    }
}
```
com.styz.rules.MyRules
```java
@Configuration
public class MyRules {
    @Bean
    @Primary
    public IRule getRobbinRule(){
        return new RandomRule();
    }
}
```
新建自定义负载均衡算法
com.styz.microservice.robbin.CountRobbinRule
```java
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
```
修改配置文件类com.styz.microservice.config.ConsummerConfigBean
```java
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
}
```
启动注册中心，启动服务提供者集群，启动消费者，访问http://localhost:7111/consummer/getById/1 查看负载均衡情况。

# 引入Feign负载均衡

RestTemplate+robbin是基于微服务名称封装httpclient的方式完成的服务调用。

而Feign是在此基础上又进行了一次封装，使得消费端可以通过注解+接口的方式
用接口的方式完成服务的调用，有点类似于Dubbo中接口调用一样。只是底层实现不一样，
Dubbo是RPC，而Feign是http的Restful调用。
## 创建microservicecloud-employ-consummer-feign模块
修改pom文件添加Feign支持
```xml
<dependencies>
        <dependency>
            <groupId>com.com.styz.microservicecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
    </dependencies>
```
2.修改microservicecloud-api 添加Feign接口com.styz.microservicecloud.service.EmployClientService
```java

@FeignClient(value = "EMPLOYSERVICE")
public interface EmployClientService {
    //注意此时的url是服务端的全路径，不是客户端哦
    @RequestMapping(value = "/employ/getAllEmployes",method = RequestMethod.POST)
    public List<Employ> getAllEmployes();

    @RequestMapping(value = "/employ/getEmployById/{id}",method = RequestMethod.POST)
    public Employ getEmployById(@PathVariable("id")long id);

}
```
修改com.styz.microservice.controller.EmployConsumerController
```java
@RestController
@RequestMapping("/comsummer")
public class EmployConsumerController {
    //EmployClientService 为Feign接口
    @Autowired
    private EmployClientService clientService;

    @RequestMapping("/getById/{id}")
    public Employ getEmployById(@PathVariable("id") long id){
       return clientService.getEmployById(id);
    }
    @RequestMapping("/getAllEmployes")
    public List<Employ> getAllEmployes(){
        return clientService.getAllEmployes();
    }
}
```
修改启动类com.styz.microservice.EmployComsumerApplication
```java
@SpringBootApplication
@EnableDiscoveryClient
//注意EnableFeignClients 一定要扫描到@FeignClient的包否则会报错
@EnableFeignClients(basePackages = "com.styz.microservicecloud.service")
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class,args);
    }
}
```
此时再次访问http://localhost:7111/consummer/getById/1 查看负载均衡情况

# 添加熔断Hystrix支持
Hystrix是什么？请自行百度。
## 新建microservicecloud-employ-consummer-hystrix 模块
修改pom文件
```xml
<dependencies>
        <dependency>
            <groupId>com.com.styz.microservicecloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
```
修改启动类添加
```java
@SpringBootApplication
@EnableDiscoveryClient
//注意EnableFeignClients 一定要扫描到@FeignClient的包否则会报错
@EnableFeignClients(basePackages = "com.styz.microservicecloud.service")
//添加熔断机制
@EnableHystrix
@ComponentScan("com.styz")
public class EmployComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployComsumerApplication.class,args);
    }
}
```
修改controller 添加@HystrixCommand，
一般情况下熔断一般实现在服务提供端，我这里只是demo就实现在消费端了。
@HystrixCommand能捕获异常可以根据异常不同，进行不同的熔断操作。
```java
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
```
@HystrixCommand 业务代码耦合严重，方法容易膨胀，所以有了Fegin熔断机制
可以在接口上面实现熔断，从而解藕
修改com.styz.microservicecloud.service.EmployClientService
```java

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
```
```java
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
``` 
开启Fegin降级机制
```yaml
feign:
  hystrix:
    enabled: true
```
此时称之为Feign+fallback 服务降级
何谓服务降级：简单的说就是当整个系统资源不够用时，为了满足某些服务依然可用，就需要忍痛将其他服务停掉，将系统资源释放
用以支持其他服务。此时停掉的服务不能影响整个系统的使用，就需要在消费端在访问不到服务的情况下，将服务进行降级处理。

启动消费者测试一下服务的熔断和降级看看。

# 添加监控HystrixDashboard支持
## hystrix.stream
## turbon

# 添加网关Zuul支持

# 添加配置服务Config支持

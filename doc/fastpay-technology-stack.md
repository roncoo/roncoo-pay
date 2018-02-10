Fastpay技术选型

freewolf 180220 created

秉承使用新技术的原则，保证该系统在3年内在国内处于技术先进、高性能和高可靠性。

项目构建工具

- Maven: 没什么好说的，目前主流，大家都熟悉，这里我保守一些，没必要非换Gradle。Maven



基础组件 - Spring MVC vs Spring Boot

- 没得选了，既然要服务化，SpringCloud组件必选，Spring Cloud了还M啥VC？哈哈。Spring Boot 



安全组件 - Spring Security vs Shiro

- 既然Spring了，就Security吧，并不难，并且JWT支持的好。 待定



数据组件 - JPA vs MyBatis

- Spring 我更看好JPA，验证，JPQL都简单，MyBatis也行。待定



反向代理 - Zuul vs Nginx

- 反向代理为了动态filter，牺牲一点性能也无所谓。既然Cloud就Zuul吧。Zuul



发现服务 - Eureka vs Consul

- Eureka 是 Netflix的发现服务，主要针对AP，只有HTTP支持，当一个服务挂掉，不能马上发现的
- Consul 的话目前看比较完整，主要针对CA，这个我也没用过
- 待定



配置中心 - Spring Config Center

- 目前不建议支持 没啥太大用



Log组件 - Log4j 2 vs Logback

- 都可以 Log4j就算了，性能太差，其他都可以Log4j 2新一些，全异步了吧，Logback更多 待定



消息服务 - ActiveMQ vs RabbitMQ

- 龙果用的ActiveMQ，但是更新很少了，问题较多，金融行业还是RabbitMQ应用更广。RabbitMQ



RPC - gRPC vs Thrift

- 如果有的话，还是gRPC稍好，毕竟Fabric上性能表现很优秀。gRPC



服务跟踪 - Spring Sleuth vs Zipkin

- 我都不是太熟悉，看看第一步要不要集成待定



流程管理BPM Flowable vs Activiti

- Activiti 既然 老大反水 又开始新征程，我们这里激进一点吧，本来就没啥区别。Flowable



服务监控 Spring Admin Server + Actuator

- Spring体系没啥别的可选吧 


断路器 - Hystrix

- 既然Cloud 就它了


数据库 - Mysql and Redis 

- 这里就不激进了 毕竟是金融系统



发号器 - 用我们自己的吧，可以集成到Zuul中



前端框架

- 从Angular、React 和 Vue中三选一，Vue目前文档最完善，国内团队支持最好，饿了吗的前端框架最完整，文档也全，也稳定。十万颗星的国产项目，源于Google，没法不支持了。 Vue
- 没得选了 Element 作为组件库就好了
- 直接跑在Nginx下就好了
  











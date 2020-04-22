![logo](logo.png "logo") 

# 龙果支付系统 roncoo-pay

龙果支付系统（roncoo-pay）是国内首款开源的互联网支付系统，拥有独立的账户体系、用户体系、支付接入体系、支付交易体系、对账清结算体系。目标是打造一款集成主流支付方式且轻量易用的支付收款系统，满足互联网业务系统打通支付通道实现支付收款和业务资金管理等功能。

###### 主要特点：

1. 拥有支付系统通用的支付通道接入、用户管理、资金账户管理、对账清算管理、支付订单管理等功能；

2. 已接通支付宝支付和微信支付的多种支付方式，满足大部分企业业务系统的支付需求；

3. 独立的支付网关接入系统，为下游商户或业务平台提供统一支付接入接口，轻松实现统一支付接入；

4. 项目代码免费开源且定期更新维护，扩展自由、使用无忧；

5. 配套完善的系统使用文档、部署文档、视频教程，学习使用更轻松；

6. 拥有活跃的产品技术学习交流社群，学习交流更高效；

7. 由专业的支付系统产品技术团队提供服务支持，专业性及持续性有保障；

----------------------------------------------------------------------------------

###### 应用架构：
![应用架构图](应用架构图.jpg "应用架构图")

###### 支付流程：
![支付流程图](pay-flow.png "支付流程图")

----------------------------------------------------------------------------------

##### 技术框架
| 技术 | 描述|
|---|--- |
| [Spring Boot](https://spring.io/projects/spring-boot) | 核心框架 |
| [Apache Shiro](http://shiro.apache.org)  | 安全框架 |
| [MyBatis](http://www.mybatis.org/mybatis-3/zh/index.html) | ORM框架 |
| [Druid](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98) | 数据连接池 |
| [Redis](https://redis.io) | 缓存数据库 |
| [Ehcache](http://www.ehcache.org/) | 进程内缓存框架 |
| [Maven](http://maven.apache.org/) | 项目管理 |

##### 项目结构
```
roncoo-pay //龙果支付
|
├── roncoo-pay-app-notify //商户通知模块
|
├── roncoo-pay-app-order-polling //订单轮询模块
|
├── roncoo-pay-app-reconciliation //交易对账模块
|
├── roncoo-pay-app-settlement //交易结算模块
|
├── roncoo-pay-common-core //公共基础模块，不需要单独部署
|
├── roncoo-pay-service //核心业务模块，不需要单独部署
|
├── roncoo-pay-web-boss //运营后台模块
|
├── roncoo-pay-web-gateway //支付网关模块
|
├── roncoo-pay-web-merchant //商户后台模块
|
├── roncoo-pay-web-sample-shop //模拟商城模块
```

##### 模块介绍
> roncoo-pay-app-notify

说明：商户通知模块，把交易成功的订单信息按一定的通知策略通知相应的商户

> roncoo-pay-app-order-polling

说明：订单轮询模块，按制定的查询策略对平台支付中的订单进行结果查询，然后对获取到的订单结果做相应的处理（注意：除了轮询还能通过异步通知获取订单结果进行处理）

> roncoo-pay-app-reconciliation

说明：交易对账模块，每天定时对前一天平台的交易订单和银行方（例如：微信、支付宝...）订单进行匹配校验，校验订单状态、手续费、交易金额等

> roncoo-pay-app-settlement

说明：交易结算模块，每天定时对前一天已对完账后确认无误的交易订单进行结算，把钱结算给对应的商家和平台

> roncoo-pay-common-core

说明：公共基础模块，包含公共的工具类、枚举、配置、基础实体、基础DAO层等

> roncoo-pay-service

说明：核心业务模块，整个系统的核心，所有业务功能（下单，查询、账户操作......）的实现，所有web工程和app服务都需要引用该模块

> roncoo-pay-web-boss

说明：运营后台模块，提供对支付系统功能的配置和维护，例如维护银行信息、支付产品，商户信息、费率、交易查询等

> roncoo-pay-web-gateway

说明：支付网关模块，对商家提供对外的网关支付接口（包含支付下单，支付查询......）

> roncoo-pay-web-merchant

说明：商户后台模块，提供商户查看交易订单信息

> roncoo-pay-web-sample-shop

说明：模拟商城模块，提供向支付系统发起支付请求的商城，提供测试方法

---------

在线演示（后台管理）：http://demo.pay.roncoo.com/boss

在线演示(商户系统)： http://demo.pay.roncoo.com/mch

系统操作说明：https://blog.roncoo.com/article/124375

系统详细介绍：https://blog.roncoo.com/article/124373

系统搭建部署：https://blog.roncoo.com/article/124511


###### 欢迎加群
QQ群：604128060   

欢迎加群，共同探讨互联网支付行业的技术经验！

龙果支付系统官网：http://pay.roncoo.net

![logo](logo.png "logo") 


 







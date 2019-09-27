package com.roncoo.pay;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppSettlementApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
//        SpringApplication.run(AppSettlementApplication.class, args);
        context = new SpringApplicationBuilder().sources(AppSettlementApplication.class).web(WebApplicationType.NONE).run(args);
    }

}


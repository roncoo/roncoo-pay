package com.roncoo.pay;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppReconciliationApplication {

    public static ConfigurableApplicationContext context;


    public static void main(String[] args) {
//        SpringApplication.run(AppReconciliationApplication.class, args);
        context = new SpringApplicationBuilder().sources(AppReconciliationApplication.class).web(WebApplicationType.NONE).run(args);
    }

}


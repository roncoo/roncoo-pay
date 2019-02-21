package com.roncoo.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppSettlementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSettlementApplication.class, args);
    }

}


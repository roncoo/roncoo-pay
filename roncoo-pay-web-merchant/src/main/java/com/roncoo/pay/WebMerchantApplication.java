package com.roncoo.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WebMerchantApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMerchantApplication.class, args);
    }

}


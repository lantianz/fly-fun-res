package com.ltz.flyfun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FlyFunApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyFunApplication.class, args);
    }

}

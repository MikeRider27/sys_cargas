package com.syscargas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SysCargasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysCargasApplication.class, args);
    }
}

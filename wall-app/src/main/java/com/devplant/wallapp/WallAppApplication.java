package com.devplant.wallapp;

import com.devplant.wallapp.proprties.InitialAccountProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(InitialAccountProperties.class)
public class WallAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallAppApplication.class, args);
    }
}

package com.example.demojava21;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Demojava21Application {

    public static void main(String[] args) {
        SpringApplication.run(Demojava21Application.class, args);
    }

}

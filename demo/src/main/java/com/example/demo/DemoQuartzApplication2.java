package com.example.demo;

import org.quartz.Scheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoQuartzApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(DemoQuartzApplication2.class, args);

        System.out.println();
    }
}
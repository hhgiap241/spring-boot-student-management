package com.kms.mywebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class MyWebAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MyWebAppApplication.class, args);
    }

    @Autowired
    ApplicationContext applicationContext;
    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(applicationContext.getBeanDefinitionNames())
                .stream()
                .forEach(System.out::println);
    }
}

package com.stella.assignment2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Assignment2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started successfully!");
    }
}
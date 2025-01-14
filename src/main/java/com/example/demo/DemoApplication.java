package com.example.demo;

import com.example.demo.utils.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        DatabaseConnection.getConnection();
        SpringApplication.run(DemoApplication.class, args);
    }

}

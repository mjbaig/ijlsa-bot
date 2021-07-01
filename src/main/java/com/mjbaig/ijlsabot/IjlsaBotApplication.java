package com.mjbaig.ijlsabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mjbaig.ijlsabot"})
public class IjlsaBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(IjlsaBotApplication.class, args);
    }

}

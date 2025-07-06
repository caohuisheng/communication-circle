package com.chs.circlepost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.chs")
public class CirclePostApplication {

    public static void main(String[] args) {
        SpringApplication.run(CirclePostApplication.class, args);
    }

}

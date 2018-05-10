package me.xiaff.crawler.patent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PatentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatentApplication.class, args);
    }
}

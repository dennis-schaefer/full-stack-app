package de.schaeferd.fullstackapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FullStackAppApplication
{
    static void main(String[] args)
    {
        SpringApplication.run(FullStackAppApplication.class, args);
    }

    @RestController
    static class HelloWorldController
    {
        @GetMapping("/api/hello")
        String hello()
        {
            return "Hello, World!";
        }
    }
}

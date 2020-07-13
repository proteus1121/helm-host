package com.ishchenko.artem.helm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootServerlessApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootServerlessApplication.class, args);
  }
}

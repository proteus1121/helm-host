package com.ishchenko.artem.helm;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootServerlessApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootServerlessApplication.class, args);
  }

  @Bean
  //TODO credentials
  public AmazonS3 getClient() {
    return AmazonS3ClientBuilder.defaultClient();
  }
}

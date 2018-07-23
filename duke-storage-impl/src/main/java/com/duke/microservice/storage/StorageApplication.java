package com.duke.microservice.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created pc on 2018/7/2
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StorageApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

    /**
     * 解决跨域问题
     *
     * @param registry 跨域注册
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }
}

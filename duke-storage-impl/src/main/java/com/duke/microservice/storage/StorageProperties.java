package com.duke.microservice.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created duke on 2018/7/2
 */
@Configuration
@ConfigurationProperties(prefix = "duke.storage", ignoreUnknownFields = false)
public class StorageProperties {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

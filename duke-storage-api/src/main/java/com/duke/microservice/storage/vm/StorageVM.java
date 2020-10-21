package com.duke.microservice.storage.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageVM {

    private String id;

    private String name;

    private String suffix;

    private String serviceId;

    private String path;

    private String url;

    private Integer size;

    private Integer status;

    private Integer type;

    private String md5;

    public StorageVM() {
    }

    public StorageVM(String id, String name, String suffix, String serviceId, String path, Integer size, Integer status, Integer type, String md5) {
        this.id = id;
        this.name = name;
        this.suffix = suffix;
        this.serviceId = serviceId;
        this.path = path;
        this.size = size;
        this.status = status;
        this.type = type;
        this.md5 = md5;
    }
}

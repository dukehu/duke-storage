package com.duke.microservice.storage.domain.basic;

import java.util.Date;

public class Pdf2WordRecord {
    private String id;

    private String name;

    private String fileId;

    private Date transformTime;

    private String takeUpTime;

    private String transformedPath;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getTransformTime() {
        return transformTime;
    }

    public void setTransformTime(Date transformTime) {
        this.transformTime = transformTime;
    }

    public String getTakeUpTime() {
        return takeUpTime;
    }

    public void setTakeUpTime(String takeUpTime) {
        this.takeUpTime = takeUpTime;
    }

    public String getTransformedPath() {
        return transformedPath;
    }

    public void setTransformedPath(String transformedPath) {
        this.transformedPath = transformedPath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
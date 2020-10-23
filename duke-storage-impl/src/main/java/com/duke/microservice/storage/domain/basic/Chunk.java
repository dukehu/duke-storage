package com.duke.microservice.storage.domain.basic;

public class Chunk {
    private String id;

    private Integer chunkNumber;

    private Long chunkSize;

    private Long currentChunkSize;

    private Long totalSize;

    private Integer totalChunks;

    private String path;

    private String md5;

    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Long getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(Long currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Chunk() {
    }

    public Chunk(String id, Integer chunkNumber, Long chunkSize, Long currentChunkSize, Long totalSize, Integer totalChunks, String path, String md5, String fileName) {
        this.id = id;
        this.chunkNumber = chunkNumber;
        this.chunkSize = chunkSize;
        this.currentChunkSize = currentChunkSize;
        this.totalSize = totalSize;
        this.totalChunks = totalChunks;
        this.path = path;
        this.md5 = md5;
        this.fileName = fileName;
    }
}
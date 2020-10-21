package com.duke.microservice.storage.service;

public interface IFilePreviewService {

    /**
     * 转pdf文件
     *
     * @param serviceId 服务id
     * @param fileId    附件id
     */
    String toPdfFile(String serviceId, String fileId);
}

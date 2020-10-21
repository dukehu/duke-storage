package com.duke.microservice.storage.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IFileUploadService {

    /**
     * 文件上传
     *
     * @param multipartFile 文件对象
     * @param serviceId     服务id
     */
    void fileUpload(MultipartFile multipartFile, String serviceId, String md5);

    /**
     * 多文件上传
     *
     * @param serviceId 服务id
     * @param request   请求
     */
    void fileBatchUpload(String serviceId, HttpServletRequest request);

}

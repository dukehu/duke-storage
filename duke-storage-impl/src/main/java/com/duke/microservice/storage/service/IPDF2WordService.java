package com.duke.microservice.storage.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IPDF2WordService {

    /**
     * pdf转word
     *
     * @param multipartFile 前端上传的文件
     * @param serviceId     服务id
     * @param md5           文件MD5值
     */
    String pdf2Word(MultipartFile multipartFile, String serviceId, String md5);

    /**
     * 下载word
     *
     * @param fileId   文件id
     * @param request  请求
     * @param response 响应
     */
    void downLoad(String fileId, HttpServletRequest request, HttpServletResponse response);
}

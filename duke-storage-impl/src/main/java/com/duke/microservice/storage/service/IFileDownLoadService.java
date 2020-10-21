package com.duke.microservice.storage.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFileDownLoadService {

    /**
     * 文件下载
     *
     * @param fileId   附件id
     * @param request  请求
     * @param response 响应
     */
    void fileDownLoad(String fileId, HttpServletRequest request, HttpServletResponse response);

}

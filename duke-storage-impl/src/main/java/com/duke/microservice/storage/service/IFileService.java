package com.duke.microservice.storage.service;

import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.vm.StorageVM;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface IFileService {

    /**
     * 校验附件id有效性
     *
     * @param fileId 附件id
     * @return Storage
     */
    Storage exist(String fileId);

    /**
     * 根据服务id查询附件
     *
     * @param serviceId 服务id
     * @param page      页码
     * @param size      每页条数
     * @return PageInfo<StorageVM>
     */
    PageInfo<StorageVM> selectFilesByServiceId(String serviceId, Integer page, Integer size);

    /**
     * 根据附件id删除
     *
     * @param fileId 附件id
     */
    void deleteById(String fileId);

    /**
     * 上传文件
     *
     * @param multipartFile    multipartFile
     * @param md5              文件MD5
     * @param serviceId        服务id
     * @param relativeFilePath 文件存储路径
     * @return file
     */
    String uploadFile(MultipartFile multipartFile, String md5, String serviceId, String relativeFilePath);

    /**
     * 秒传
     *
     * @param serviceId         服务id
     * @param fileNameNotSuffix 没有后缀的文件名
     * @param md5               文件MD5
     * @return boolean
     */
    Storage secondUpload(String serviceId, String fileNameNotSuffix, String md5);

    /**
     * 下载
     *
     * @param response 响应
     * @param fileName 文件原始名
     * @param file     文件
     * @throws IOException 文件流
     */
    void download(HttpServletResponse response, String fileName, File file) throws IOException;

    /**
     * 根据MD5值查询
     *
     * @param md5 md5值
     */
    Boolean selectByMD5(String md5);
}

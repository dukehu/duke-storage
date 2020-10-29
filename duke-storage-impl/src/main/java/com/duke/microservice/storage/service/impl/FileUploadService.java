package com.duke.microservice.storage.service.impl;

import com.duke.framework.utils.FileUtils;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.mapper.basic.StorageMapper;
import com.duke.microservice.storage.mapper.extend.StorageExtendMapper;
import com.duke.microservice.storage.service.IFileService;
import com.duke.microservice.storage.service.IFileUploadService;
import com.duke.microservice.storage.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created duke on 2018/7/2
 */
@Service
@Transactional
public class FileUploadService implements IFileUploadService {

    private Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StorageExtendMapper storageExtendMapper;

    @Autowired
    private IFileService fileService;

    public void fileUpload(MultipartFile multipartFile, String serviceId, String md5) {
        ValidationUtils.notEmpty(multipartFile, "文件不可为空！");
        ValidationUtils.notEmpty(serviceId, "服务id不可为空！");
        fileService.uploadFile(multipartFile, md5, serviceId, FileUtils.getRelativeFilePath(serviceId));
    }

    public void fileBatchUpload(String serviceId, HttpServletRequest request) {
        // todo 获得用户信息
        String userId = "b66a3fe7-8fdd-11e8-bcd8-18dbf21f6c28";
        ValidationUtils.notEmpty(serviceId, "服务id不可为空！");
        // 创建一个多分解的容器
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        // 设置编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        // 设置文件的最大上传大小
        commonsMultipartResolver.setMaxUploadSize(storageProperties.getMaxUploadSize());

        try {
            commonsMultipartResolver.resolveMultipart(request);
        } catch (MaxUploadSizeExceededException e) {
            throw new MaxUploadSizeExceededException(e.getMaxUploadSize(), e);
        }

        //判断是否有文件上传
        if (commonsMultipartResolver.isMultipart(request)) {
            // 将request转换成多分解请求
            MultipartHttpServletRequest multipartHttpServletRequest
                    = commonsMultipartResolver.resolveMultipart(request);
        }

        // todo 多文件上传先不做，需要用的时候再去处理
    }

    @Override
    public void secondUpload(String fileName, String md5, String serviceId) {
        Date date = new Date();
        String fileNameNotSuffix = fileName.substring(0, fileName.lastIndexOf("."));
        List<Storage> storages = storageExtendMapper.selectByMD5(md5);
        Storage storage = storages.get(0);
        storage.setId(UUID.randomUUID().toString());
        storage.setServiceId(serviceId);
        storage.setName(fileNameNotSuffix);
        storage.setUploadTime(date);
        storage.setDeleteTime(date);
        storageMapper.insert(storage);
    }
}

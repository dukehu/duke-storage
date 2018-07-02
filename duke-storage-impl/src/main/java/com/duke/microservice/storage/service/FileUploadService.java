package com.duke.microservice.storage.service;

import com.duke.microservice.storage.StorageConstants;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.mapper.basic.StorageMapper;
import com.duke.microservice.storage.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created duke on 2018/7/2
 */
@Service
@Transactional
public class FileUploadService {

    private Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private StorageMapper storageMapper;

    /**
     * 文件上传
     *
     * @param multipartFile 文件对象
     * @param serviceId     服务id
     */
    public void fileUpload(MultipartFile multipartFile, String serviceId) {
        // todo 获得用户信息
        String userId = "duke";

        // todo 校验文件是否为空，需要重新改
        if (ObjectUtils.isEmpty(multipartFile)) {
            // 文件为空
        }

        if (StringUtils.isNotBlank(serviceId)) {
            // todo 校验服务id是否有效
        } else {
            serviceId = "";
        }

        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件名称，如time.png
        String fileName = multipartFile.getOriginalFilename();
        // 街截取文件后缀
        String fileSuffix = FileUtil.getFileSuffix(fileName);
        // todo 校验文件名称长度

        String relativeFilePath = FileUtil.getRelativeFilePath(serviceId);
        File file = new File(storageProperties.getPath() + relativeFilePath);

        if (!file.exists()) {
            Boolean mkdirsed = file.mkdirs();
            if (!mkdirsed) {
                // todo 抛出异常，创建文件夹失败
            }
        }
        String id = UUID.randomUUID().toString();
        String path = storageProperties.getPath() + relativeFilePath + "/" + id + fileSuffix;

        // 上传文件并将文件基本信息保存到数据库中
        try {
            log.info("文件开始开始，上传路径：" + path);
            multipartFile.transferTo(new File(path));
            log.info("文件上传结束");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();

        // 保存文件基本信息
        Storage storage = new Storage();
        storage.setId(id);
        storage.setName(fileName);
        storage.setSuffix(fileSuffix);
        storage.setServiceId(serviceId);
        storage.setPath(path);
        storage.setSize(new Long(fileSize).intValue());
        storage.setStatus(StorageConstants.FILE_STATUS.EXIST.getCode());
        storage.setUserId(userId);
        // todo 处理文件类型
        storage.setType(1);
        storage.setUploadTime(date);
        storage.setDeleteTime(date);
        storageMapper.insert(storage);
    }
}

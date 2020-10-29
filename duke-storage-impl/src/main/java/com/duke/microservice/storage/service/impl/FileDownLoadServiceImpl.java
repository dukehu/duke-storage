package com.duke.microservice.storage.service.impl;

import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.exception.BusinessException;
import com.duke.microservice.storage.mapper.basic.StorageMapper;
import com.duke.microservice.storage.service.IFileDownLoadService;
import com.duke.microservice.storage.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created duke on 2018/7/3
 */
@Service
@Transactional
public class FileDownLoadServiceImpl implements IFileDownLoadService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileDownLoadServiceImpl.class);

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private IFileService fileService;

    @Override
    public void fileDownLoad(String fileId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Storage storage = fileService.exist(fileId);
            File file = new File(storage.getPath());
            String fileName = storage.getName() + "." + storage.getSuffix();
            fileService.download(response, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

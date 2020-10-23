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
            if (!file.exists()) {
                throw new BusinessException("数据不存在，无法下载");
            }
            response.reset();
            response.setContentType("application/x-download");
            response.setCharacterEncoding("utf-8");
            FileInputStream is = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();

            String filename = storage.getName() + "." + storage.getSuffix();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"iso-8859-1"));
            byte[] bytes = new byte[1024];
            int len;
            LOGGER.info("文件下载开始：" + filename + "（" + fileId + "）");
            while ((len = is.read(bytes)) != -1) {
                out.write(bytes, 0, len);
                // out.flush();
            }
            LOGGER.info("文件下载结束：" + filename + "（" + fileId + "）");
            is.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

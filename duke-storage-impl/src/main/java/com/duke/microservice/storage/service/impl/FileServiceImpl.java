package com.duke.microservice.storage.service.impl;

import com.duke.framework.utils.FileUtils;
import com.duke.microservice.storage.StorageConstants;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.exception.BusinessException;
import com.duke.microservice.storage.mapper.basic.StorageMapper;
import com.duke.microservice.storage.mapper.extend.StorageExtendMapper;
import com.duke.microservice.storage.service.IFileService;
import com.duke.microservice.storage.utils.ValidationUtils;
import com.duke.microservice.storage.vm.StorageVM;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StorageExtendMapper storageExtendMapper;

    /**
     * 校验附件id有效性
     *
     * @param fileId 附件id
     * @return Storage
     */
    public Storage exist(String fileId) {
        ValidationUtils.notEmpty(fileId, "附件id不能为空！");
        Storage storage = storageMapper.selectByPrimaryKey(fileId);
        if (ObjectUtils.isEmpty(storage)) {
            throw new BusinessException("id为：" + fileId + "的附件不存在！");
        }
        return storage;
    }

    @Transactional(readOnly = true)
    public PageInfo<StorageVM> selectFilesByServiceId(String serviceId, Integer page, Integer size) {
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(size)) {
            page = 0;
            size = 10;
        }
        String storagePath = storageProperties.getPath();
        PageHelper.startPage(page, size);
        List<Storage> storages = storageExtendMapper.selectFilesByServiceId(serviceId);
        List<StorageVM> storageVMS = new Page<>();
        if (!CollectionUtils.isEmpty(storages)) {
            for (Storage storage : storages) {
                StorageVM storageVM =
                        new StorageVM(storage.getId(),
                                storage.getName(),
                                storage.getSuffix(),
                                storage.getServiceId(),
                                storage.getPath().substring(storagePath.length()),
                                storage.getSize(),
                                storage.getStatus(),
                                storage.getType(),
                                storage.getMd5());
                storageVMS.add(storageVM);
            }
            BeanUtils.copyProperties(storages, storageVMS);
        }
        return new PageInfo<>(storageVMS);
    }

    @Override
    @Transactional
    public void deleteById(String fileId) {
        Storage storage = this.exist(fileId);
        File file = new File(storage.getPath());
        // todo 删除对应预览时生成的pdf文件
        file.deleteOnExit();
        storageMapper.deleteByPrimaryKey(fileId);
    }

    @Override
    @Transactional
    public String uploadFile(MultipartFile multipartFile, String md5, String serviceId, String relativeFilePath) {

        String userId = "b66a3fe7-8fdd-11e8-bcd8-18dbf21f6c28";
        Date date = new Date();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileNameNotSuffix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        File folder = new File(storageProperties.getPath() + relativeFilePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(storageProperties.getPath() + relativeFilePath + "/" + md5 + "." + suffix);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = UUID.randomUUID().toString();
        // 保存文件信息开始
        Storage storage = new Storage();
        storage.setId(UUID.randomUUID().toString());
        storage.setName(fileNameNotSuffix);
        storage.setSuffix(suffix);
        storage.setServiceId(serviceId);
        storage.setPath(storageProperties.getPath() + relativeFilePath + "/" + originalFilename);
        storage.setSize(Integer.parseInt(String.valueOf(multipartFile.getSize())));
        storage.setStatus(StorageConstants.FILE_STATUS.EXIST.getCode());
        storage.setUserId(userId);
        // todo 处理文件类型
        storage.setType(1);
        storage.setUploadTime(date);
        storage.setDeleteTime(date);
        storage.setMd5(md5);
        storageMapper.insert(storage);
        // 保存文件信息结束
        return id;
    }

    @Override
    public Storage secondUpload(String serviceId, String fileNameNotSuffix, String md5) {
        Date date = new Date();
        String userId = "b66a3fe7-8fdd-11e8-bcd8-18dbf21f6c28";
        List<Storage> storages = storageExtendMapper.selectByMD5(md5);
        if (CollectionUtils.isEmpty(storages)) {
            return null;
        } else {
            Storage storage = storages.get(0);
            storage.setId(UUID.randomUUID().toString());
            storage.setServiceId(serviceId);
            storage.setName(fileNameNotSuffix);
            storage.setUserId(userId);
            storage.setUploadTime(date);
            storage.setDeleteTime(date);
            storageMapper.insert(storage);
            return storage;
        }
    }

    @Override
    public void download(HttpServletResponse response, String fileName, File file) throws IOException {
        if (!file.exists()) {
            throw new BusinessException("数据不存在，无法下载");
        }
        response.reset();
        response.setContentType("application/x-download");
        response.setCharacterEncoding("utf-8");
        FileInputStream is = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        byte[] bytes = new byte[1024];
        int len;
        log.info("文件下载开始：" + fileName);
        while ((len = is.read(bytes)) != -1) {
            out.write(bytes, 0, len);
            // out.flush();
        }
        log.info("文件下载结束：" + fileName);
        is.close();
        out.close();
    }

    @Override
    public Boolean selectByMD5(String md5) {
        return !CollectionUtils.isEmpty(storageExtendMapper.selectByMD5(md5));
    }

}

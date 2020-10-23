package com.duke.microservice.storage.service.impl;

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

import java.io.File;
import java.util.List;

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
    public Boolean selectByMD5(String md5) {
        return !CollectionUtils.isEmpty(storageExtendMapper.selectByMD5(md5));
    }

}

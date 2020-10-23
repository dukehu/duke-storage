package com.duke.microservice.storage.service;

import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.vm.StorageVM;
import com.github.pagehelper.PageInfo;

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
     * 根据MD5值查询
     *
     * @param md5 md5值
     */
    Boolean selectByMD5(String md5);
}

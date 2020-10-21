package com.duke.microservice.storage.mapper.extend;

import com.duke.microservice.storage.domain.basic.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created duke on 2018/7/3
 */
@Mapper
public interface StorageExtendMapper {

    /**
     * 根据文件md5值查询
     *
     * @param md5 文件md5值
     * @return List<Storage>
     */
    List<Storage> selectByMD5(@Param("md5") String md5);

    /**
     * 根据服务id查询
     *
     * @param serviceId 服务id
     * @return List<Storage>
     */
    List<Storage> selectFilesByServiceId(@Param("serviceId") String serviceId);
}

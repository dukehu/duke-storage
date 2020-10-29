package com.duke.microservice.storage.mapper.extend;

import com.duke.microservice.storage.domain.basic.Pdf2WordRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface Pdf2WordRecordExtendMapper {

    /**
     * 根据文件id查找
     *
     * @param fileId 文件id
     * @return list
     */
    List<Pdf2WordRecord> selectByFileId(@Param("fileId") String fileId);

}
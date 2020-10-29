package com.duke.microservice.storage.mapper.basic;

import com.duke.microservice.storage.domain.basic.Pdf2WordRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Pdf2WordRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(Pdf2WordRecord record);

    Pdf2WordRecord selectByPrimaryKey(String id);

    List<Pdf2WordRecord> selectAll();

    int updateByPrimaryKey(Pdf2WordRecord record);
}
package com.duke.microservice.storage.mapper.basic;

import com.duke.microservice.storage.domain.basic.Chunk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChunkMapper {
    int deleteByPrimaryKey(String id);

    int insert(Chunk record);

    Chunk selectByPrimaryKey(String id);

    List<Chunk> selectAll();

    int updateByPrimaryKey(Chunk record);
}
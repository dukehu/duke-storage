package com.duke.microservice.storage.mapper.extend;

import com.duke.microservice.storage.domain.basic.Chunk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChunkExtendMapper {

    /**
     * 根据文件标识和块码查询
     *
     * @param identifier  文件标识
     * @param chunkNumber 当前文件块
     * @return Chunk
     */
    Chunk selectByIdentifierAndChunkNumber(String identifier, int chunkNumber);

    /**
     * 根据md5值查找
     *
     * @param md5 MD5
     * @return list
     */
    List<Chunk> selectByMd5(String md5);
}

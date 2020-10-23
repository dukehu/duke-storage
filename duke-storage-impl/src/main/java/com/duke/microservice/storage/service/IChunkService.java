package com.duke.microservice.storage.service;

import com.duke.microservice.storage.domain.basic.Chunk;
import org.springframework.web.multipart.MultipartFile;

/**
 * 块信息service
 */
public interface IChunkService {

    /**
     * 文件分块上传
     *
     * @param file             块文件
     * @param chunkNumber      当前块
     * @param chunkSize        每一块文件大小
     * @param currentChunkSize 当前块大小
     * @param totalSize        文件大小
     * @param totalChunks      分片数
     * @param md5              上传文件md值
     * @param fileName         文件名
     * @param serviceId        服务id
     */
    void fileUploadChunk(MultipartFile file,
                         Integer chunkNumber,
                         Long chunkSize,
                         Long currentChunkSize,
                         Long totalSize,
                         Integer totalChunks,
                         String md5,
                         String fileName,
                         String serviceId);

    /**
     * 文件块是否存在
     *
     * @param identifier  文件标识
     * @param chunkNumber 当前文件块
     * @return boolean
     */
    boolean exist(String identifier, Integer chunkNumber);

    /**
     * 文件合并
     *
     * @param fileName  文件名称
     * @param fileSize  文件大小
     * @param md5       文件MD5值
     * @param serviceId 服务名称
     */
    void fileMerge(String fileName, Integer fileSize, String md5, String serviceId);
}

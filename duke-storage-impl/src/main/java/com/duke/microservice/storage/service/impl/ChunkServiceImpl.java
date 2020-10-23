package com.duke.microservice.storage.service.impl;

import com.duke.framework.utils.FileUtils;
import com.duke.microservice.storage.StorageConstants;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Chunk;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.mapper.basic.ChunkMapper;
import com.duke.microservice.storage.mapper.basic.StorageMapper;
import com.duke.microservice.storage.mapper.extend.ChunkExtendMapper;
import com.duke.microservice.storage.service.IChunkService;
import com.duke.microservice.storage.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChunkServiceImpl implements IChunkService {

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private ChunkMapper chunkMapper;

    @Autowired
    private ChunkExtendMapper chunkExtendMapper;

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @Transactional
    public void fileUploadChunk(MultipartFile multipartFile,
                                Integer chunkNumber,
                                Long chunkSize,
                                Long currentChunkSize,
                                Long totalSize,
                                Integer totalChunks,
                                String md5,
                                String fileName,
                                String serviceId) {
        ValidationUtils.notEmpty(multipartFile, "文件不可为空！");
        ValidationUtils.notEmpty(chunkNumber, "块编码不可为空！");
        ValidationUtils.notEmpty(chunkSize, "文件分块大小不可为空！");
        ValidationUtils.notEmpty(currentChunkSize, "当前块大小不可为空！");
        ValidationUtils.notEmpty(totalSize, "文件大小不可为空！");
        ValidationUtils.notEmpty(totalChunks, "分片数不可为空！");
        ValidationUtils.notEmpty(md5, "文件md5值不可为空！");
        ValidationUtils.notEmpty(fileName, "文件名称不可为空！");
        ValidationUtils.notEmpty(serviceId, "服务id不可为空！");

        String relativeFilePath = FileUtils.getChunkRelativeFilePath(serviceId);
        File file = new File(storageProperties.getChunkStoragePath() + relativeFilePath + "/" + md5);
        // 文件名称，如time.png
        String originalFileName = multipartFile.getOriginalFilename();
        // 街截取文件后缀
        String fileSuffix = FileUtils.getFileSuffix(originalFileName);

        if (!file.exists()) {
            boolean mkdirsed = file.mkdirs();
            if (!mkdirsed) {
                // todo 抛出异常，创建文件夹失败
            }
        }

        String id = UUID.randomUUID().toString();
        String path = storageProperties.getPath() + relativeFilePath + "/" + md5 + "/" + fileName;
        try {
            multipartFile.transferTo(new File(path));
            Chunk chunk = new Chunk(id, chunkNumber, chunkSize, currentChunkSize, totalSize, totalChunks, path, md5, fileName);
            chunkMapper.insert(chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(String identifier, Integer chunkNumber) {
        Chunk chunk = chunkExtendMapper.selectByIdentifierAndChunkNumber(identifier, chunkNumber);
        return !ObjectUtils.isEmpty(chunk);
    }

    @Override
    public void fileMerge(String fileName, Integer fileSize, String md5, String serviceId) {

        try {
            // 没有后缀的文件名
            String fileNameNotSuffix = fileName.substring(0, fileName.lastIndexOf("."));
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String targetFileFolderPath = storageProperties.getPath() + FileUtils.getRelativeFilePath(serviceId);
            File targetFileFolder = new File(targetFileFolderPath);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String targetFilePath = targetFileFolderPath + "/" + md5 + "." + fileSuffix;
            Files.createFile(Paths.get(targetFilePath));
            List<Chunk> chunks = chunkExtendMapper.selectByMd5(md5);
            chunks.forEach(chunk -> {
                try {
                    //以追加的形式写入文件
                    Files.write(Paths.get(targetFilePath), Files.readAllBytes(Paths.get(chunk.getPath())), StandardOpenOption.APPEND);
                    //合并后删除该块
                    Files.delete(Paths.get(chunk.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Date date = new Date();
            Storage storage = new Storage(UUID.randomUUID().toString(),
                    fileNameNotSuffix,
                    fileSuffix,
                    serviceId,
                    targetFilePath,
                    fileSize,
                    StorageConstants.FILE_STATUS.EXIST.getCode(),
                    "b66a3fe7-8fdd-11e8-bcd8-18dbf21f6c28",
                    1,
                    date,
                    date,
                    md5);
            storageMapper.insert(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

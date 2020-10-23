package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.api.FileUploadRestService;
import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IChunkService;
import com.duke.microservice.storage.service.IFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created duke on 2018/7/2
 */
@Api(description = "文件上传接口文档")
@RestController
public class FileUploadController implements FileUploadRestService {

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IChunkService chunkService;

    @ApiImplicitParams({
    })
    @ApiOperation(value = "文件块上传", notes = "文件块上传")
    @RequestMapping(value = "/nologin/upload/chunk", method = RequestMethod.POST)
    public Response<String> fileUploadChunk(@RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestParam(value = "chunkNumber", required = false) Integer chunkNumber,
                                            @RequestParam(value = "chunkSize", required = false) Long chunkSize,
                                            @RequestParam(value = "currentChunkSize", required = false) Long currentChunkSize,
                                            @RequestParam(value = "totalSize", required = false) Long totalSize,
                                            @RequestParam(value = "totalChunks", required = false) Integer totalChunks,
                                            @RequestParam(value = "md5", required = false) String md5,
                                            @RequestParam(value = "fileName", required = false) String fileName,
                                            @RequestParam(value = "serviceId", required = false) String serviceId,
                                            HttpServletRequest request) {
        chunkService.fileUploadChunk(file,
                chunkNumber,
                chunkSize,
                currentChunkSize,
                totalSize,
                totalChunks,
                md5,
                fileName,
                serviceId);
        return Response.ok();
    }

    @ApiImplicitParams({
    })
    @ApiOperation(value = "文件合并", notes = "文件合并")
    @RequestMapping(value = "/nologin/file/merge", method = RequestMethod.POST)
    public Response<String> fileMerge(@RequestParam(value = "fileName", required = false) String fileName,
                                      @RequestParam(value = "fileSize", required = false) Integer fileSize,
                                      @RequestParam(value = "md5", required = false) String md5,
                                      @RequestParam(value = "serviceId", required = false) String serviceId) {
        chunkService.fileMerge(fileName, fileSize, md5, serviceId);
        return Response.ok();
    }

    @ApiImplicitParams({
    })
    @ApiOperation(value = "秒传", notes = "秒传")
    @RequestMapping(value = "/nologin/file/secondUpload", method = RequestMethod.POST)
    public Response<String> secondUpload(@RequestParam(value = "fileName", required = false) String fileName,
                                         @RequestParam(value = "md5", required = false) String md5,
                                         @RequestParam(value = "serviceId", required = false) String serviceId) {
        fileUploadService.secondUpload(fileName, md5, serviceId);
        return Response.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "上传文件的服务id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "md5", value = "文件md5值", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "单文件上传", notes = "单文件上传")
    @RequestMapping(value = "/nologin/upload", method = RequestMethod.POST)
    public Response<String> fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam(value = "serviceId", required = false) String serviceId,
                                       @RequestParam(value = "md5", required = false) String md5,
                                       HttpServletRequest request) {
        fileUploadService.fileUpload(file, serviceId, md5);
        return Response.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "上传文件的服务id", dataType = "string", paramType = "query", required = true)
    })
    @ApiOperation(value = "多文件上传", notes = "多文件上传")
    @RequestMapping(value = "/nologin/batch/upload", method = RequestMethod.POST)
    public Response<String> fileBatchUpload(@RequestParam(value = "serviceId", required = false) String serviceId,
                                            HttpServletRequest request) {

        fileUploadService.fileBatchUpload(serviceId, request);
        return Response.ok();
    }

}

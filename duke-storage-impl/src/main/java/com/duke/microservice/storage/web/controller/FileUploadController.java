package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.api.FileUploadRestService;
import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IFileUploadService;
import com.duke.microservice.storage.vm.StorageVM;
import com.github.pagehelper.PageInfo;
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

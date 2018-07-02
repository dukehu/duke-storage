package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "上传文件的服务id", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/storage/file_upload", method = RequestMethod.POST)
    public Response<String> fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam(value = "serviceId", required = false) String serviceId,
                                       HttpServletRequest request) {
        fileUploadService.fileUpload(file, serviceId);
        return Response.ok();
    }

}

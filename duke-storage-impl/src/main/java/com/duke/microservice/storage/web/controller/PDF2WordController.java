package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IPDF2WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created duke on 2020/10/27
 */
@Api(description = "pdf文件转word接口文档")
@RestController
public class PDF2WordController {

    @Autowired
    private IPDF2WordService pdf2WordService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "上传文件的服务id", dataType = "string", paramType = "query", required = true)
    })
    @ApiOperation(value = "pdf文件转word", notes = "pdf文件转word")
    @RequestMapping(value = "/nologin/pdf2Word", method = RequestMethod.POST)
    public Response<String> pdf2Word(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @RequestParam(value = "serviceId", required = false) String serviceId,
                                     @RequestParam(value = "md5", required = false) String md5,
                                     HttpServletRequest request) {
        return Response.ok(pdf2WordService.pdf2Word(file, serviceId, md5));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "附件id", dataType = "string", paramType = "path")
    })
    @ApiOperation(value = "转换之后word文件下载", notes = "转换之后word文件下载")
    @RequestMapping(value = "/nologin/pdf2Word/download/{fileId}", method = RequestMethod.POST)
    public Response<String> downLoad(@PathVariable(value = "fileId", required = false) String fileId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        pdf2WordService.downLoad(fileId, request, response);
        return Response.ok();
    }

}

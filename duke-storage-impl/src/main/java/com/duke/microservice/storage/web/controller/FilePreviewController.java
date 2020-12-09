package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IFilePreviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created duke on 2020/10/21
 */
@Api(description = "文件预览接口文档")
@RestController
public class FilePreviewController {


    @Autowired
    private IFilePreviewService filePreviewService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileId", value = "附件id", dataType = "string", paramType = "query", required = true)
    })
    @ApiOperation(value = "文件列表", notes = "文件列表")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('storage_file_toPdfFile')")
    @RequestMapping(value = "/toPdfFile", method = RequestMethod.GET)
    public Response<String> toPdfFile(@RequestParam(value = "serviceId", required = false) String serviceId,
                                      @RequestParam(value = "fileId", required = false) String fileId) {
        return Response.ok(filePreviewService.toPdfFile(serviceId, fileId));
    }

}

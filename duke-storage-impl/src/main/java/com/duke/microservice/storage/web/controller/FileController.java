package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IFileService;
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

/**
 * Created duke on 2020/10/21
 */
@Api(description = "文件接口文档")
@RestController
public class FileController {

    @Autowired
    private IFileService fileService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "上传文件的服务id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "int", paramType = "query", required = false)
    })
    @ApiOperation(value = "文件列表", notes = "文件列表")
    @RequestMapping(value = "/nologin/files", method = RequestMethod.GET)
    public Response<PageInfo<StorageVM>> files(@RequestParam(value = "serviceId", required = false) String serviceId,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size) {
        return Response.ok(fileService.selectFilesByServiceId(serviceId, page, size));
    }

}

package com.duke.microservice.storage.web.controller;

import com.duke.microservice.storage.common.Response;
import com.duke.microservice.storage.service.IFileDownLoadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created duke on 2018/7/3
 */
@Api(description = "文件下载接口文档")
@RestController
public class FileDownLoadController {

    @Autowired
    private IFileDownLoadService fileDownLoadService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "附件id", dataType = "string", paramType = "path")
    })
    @ApiOperation(value = "文件下载", notes = "文件下载")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('storage_file_download')")
    @RequestMapping(value = "/file_download/{fileId}", method = RequestMethod.POST)
    public Response<String> fileDownLoad(@PathVariable(value = "fileId", required = false) String fileId,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        fileDownLoadService.fileDownLoad(fileId, request, response);
        return Response.ok();
    }

}

package com.duke.microservice.storage.api;

import com.duke.microservice.storage.StorageConstants;
import com.duke.microservice.storage.common.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created duke on 2018/7/17
 */
@FeignClient(value = StorageConstants.SERVICE_ID)
public interface BreakPointUploadRestService {

    /**
     * 根据md5值判断资源文件是否存在
     *
     * @param serviceId 服务id
     * @param name      文件名称
     * @param md5       文件md5值
     * @return boolean
     */
    @RequestMapping(value = "/bp_upload/check_md5", method = RequestMethod.GET)
    Response<Boolean> checkMD5(@RequestParam(value = "serviceId", required = false) String serviceId,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "md5", required = false) String md5);
}

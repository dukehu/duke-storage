package com.duke.microservice.storage.api;

import com.duke.microservice.storage.StorageConstants;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created pc on 2018/7/2
 */
@FeignClient(value = StorageConstants.SERVICE_ID)
public class FileUploadRestService {



}

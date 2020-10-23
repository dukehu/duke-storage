package com.duke.microservice.storage.service.impl;

import com.duke.framework.utils.FileUtils;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.service.IFilePreviewService;
import com.duke.microservice.storage.service.IFileService;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FilePreviewServiceImpl implements IFilePreviewService {


    @Autowired
    private DocumentConverter documentConverter;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private IFileService fileService;

    @Override
    public String toPdfFile(String serviceId, String fileId) {
        Storage storage = fileService.exist(fileId);
        String suffix = storage.getSuffix();
        if ("pdf".equals(suffix)) {
            return storage.getPath().substring(storageProperties.getPath().length());
        }
        try {
            File sourceFile = new File(storage.getPath());
            File targetFile = new File(storageProperties.getPdfStoragePath());
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String pdfFilePath = storageProperties.getPdfStoragePath() + FileUtils.getPdfRelativeFilePath(serviceId) + "/" + storage.getMd5() + ".pdf";
            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                documentConverter.convert(sourceFile).to(pdfFile).execute();
            }
        } catch (OfficeException e) {
            e.printStackTrace();
        }
        return FileUtils.getPdfRelativeFilePath(serviceId) + "/" + storage.getMd5() + ".pdf";
    }
}

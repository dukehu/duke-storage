package com.duke.microservice.storage.service.impl;

import com.duke.framework.utils.FileUtils;
import com.duke.microservice.storage.StorageProperties;
import com.duke.microservice.storage.domain.basic.Pdf2WordRecord;
import com.duke.microservice.storage.domain.basic.Storage;
import com.duke.microservice.storage.mapper.basic.Pdf2WordRecordMapper;
import com.duke.microservice.storage.mapper.extend.Pdf2WordRecordExtendMapper;
import com.duke.microservice.storage.service.IFileService;
import com.duke.microservice.storage.service.IPDF2WordService;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PDF2WordServiceImpl implements IPDF2WordService {

    private Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private IFileService fileService;

    @Autowired
    private Pdf2WordRecordMapper pdf2WordRecordMapper;

    @Autowired
    private Pdf2WordRecordExtendMapper pdf2WordRecordExtendMapper;

    @Override
    public String pdf2Word(MultipartFile multipartFile, String serviceId, String md5) {
        long start = System.currentTimeMillis();
        // 文件原始名称
        String originalFilename = multipartFile.getOriginalFilename();
        // 没有后缀的文件名称
        String fileNameNotSuffix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        // 文件原始后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 当前时间
        Date date = new Date();
        // word文件保存完整路径
        String wordFullPath = storageProperties.getPath() + FileUtils.getWordRelativeFilePath(serviceId) + "/" + md5 + "." + "docx";

        // 需要保存的转换记录
        Pdf2WordRecord pdf2WordRecord = new Pdf2WordRecord();
        pdf2WordRecord.setId(UUID.randomUUID().toString());
        pdf2WordRecord.setName(fileNameNotSuffix + ".docx");
        pdf2WordRecord.setTransformTime(date);
        pdf2WordRecord.setUserId("b66a3fe7-8fdd-11e8-bcd8-18dbf21f6c28");
        pdf2WordRecord.setTransformedPath(wordFullPath);

        Storage storage = fileService.secondUpload(serviceId, fileNameNotSuffix, md5);
        if (ObjectUtils.isEmpty(storage)) {
            // 上传附件
            String fileId = fileService.uploadFile(multipartFile, md5, serviceId, FileUtils.getPdfRelativeFilePath(serviceId));

            // 创建Pdf工具类对象
            PdfDocument pdfDocument = new PdfDocument();
            pdfDocument.loadFromFile(storageProperties.getPath() + FileUtils.getPdfRelativeFilePath(serviceId) + "/" + md5 + "." + suffix);
            //保存为Word格式
            pdfDocument.saveToFile(wordFullPath, FileFormat.DOCX);
            long end = System.currentTimeMillis();
            pdf2WordRecord.setFileId(fileId);
            pdf2WordRecord.setTakeUpTime(String.valueOf(end - start));

        } else {
            pdf2WordRecord.setFileId(storage.getId());
            pdf2WordRecord.setTakeUpTime("0");
            pdf2WordRecord.setTransformedPath(wordFullPath);
        }
        pdf2WordRecordMapper.insert(pdf2WordRecord);
        return pdf2WordRecord.getFileId();
    }

    @Override
    public void downLoad(String fileId, HttpServletRequest request, HttpServletResponse response) {
        List<Pdf2WordRecord> pdf2WordRecords = pdf2WordRecordExtendMapper.selectByFileId(fileId);
        Pdf2WordRecord pdf2WordRecord = pdf2WordRecords.get(0);
        File file = new File(pdf2WordRecord.getTransformedPath());
        String fileName = pdf2WordRecord.getName();
        try {
            fileService.download(response, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

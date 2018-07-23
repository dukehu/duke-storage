package com.duke.microservice.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Created duke on 2018/7/2
 */
@Configuration
@ConfigurationProperties(prefix = "duke.storage", ignoreUnknownFields = false)
public class StorageProperties {

    private String path;

    private long maxUploadSize;

    private Map<String, String> tmp;

    private List<String> textType;

    private List<String> imgType;

    private List<String> officeType;

    private List<String> compressType;

    private List<String> pdfType;

    private Map<String, String> soffice;

    private Qiniu qiniu = new Qiniu();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    public List<String> getTextType() {
        return textType;
    }

    public void setTextType(List<String> textType) {
        this.textType = textType;
    }

    public Map<String, String> getTmp() {
        return tmp;
    }

    public void setTmp(Map<String, String> tmp) {
        this.tmp = tmp;
    }

    public List<String> getImgType() {
        return imgType;
    }

    public void setImgType(List<String> imgType) {
        this.imgType = imgType;
    }

    public List<String> getOfficeType() {
        return officeType;
    }

    public void setOfficeType(List<String> officeType) {
        this.officeType = officeType;
    }

    public List<String> getCompressType() {
        return compressType;
    }

    public void setCompressType(List<String> compressType) {
        this.compressType = compressType;
    }

    public List<String> getPdfType() {
        return pdfType;
    }

    public void setPdfType(List<String> pdfType) {
        this.pdfType = pdfType;
    }

    public Map<String, String> getSoffice() {
        return soffice;
    }

    public void setSoffice(Map<String, String> soffice) {
        this.soffice = soffice;
    }

    public Qiniu getQiniu() {
        return qiniu;
    }

    public void setQiniu(Qiniu qiniu) {
        this.qiniu = qiniu;
    }

    /**
     * 七牛云配置
     */
    public class Qiniu {
        private String accessKey;

        private String secretKey;

        private String bucket;

        private String path;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

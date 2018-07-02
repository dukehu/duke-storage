package com.duke.microservice.storage;

/**
 * Created duke on 2018/6/23
 */
public class StorageConstants {

    public static final String SERVICE_ID = "duke-storage";

    /**
     * 文件状态
     */
    public static enum FILE_STATUS {
        /**
         * 0：删除  1：存在
         */
        DELETED(0, "删除"),
        EXIST(1, "发布");

        private Integer code;
        private String desc;

        FILE_STATUS(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}

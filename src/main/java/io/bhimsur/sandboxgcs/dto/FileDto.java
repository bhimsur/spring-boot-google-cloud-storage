package io.bhimsur.sandboxgcs.dto;

import java.io.Serializable;

public class FileDto implements Serializable {
    private String fileName;
    private String fileUrl;

    public FileDto(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public FileDto() {
    }
}

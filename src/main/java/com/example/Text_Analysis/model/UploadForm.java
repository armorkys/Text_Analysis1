package com.example.Text_Analysis.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

    //just for uploading files
    private MultipartFile[] fileDatas;

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }

    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }

}

package com.Don_Spring_Flask.SF_Image.repository;
import com.Don_Spring_Flask.SF_Image.domain.FileUpload;

public interface ImageRepo {
    void upload(FileUpload image);
    void getScaled(String name, String path);
    FileUpload find(String code);
    void remove();
}

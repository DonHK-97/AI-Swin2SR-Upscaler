package com.Don_Spring_Flask.SF_Image.domain;

public class FileUpload {
    private String ImageName;
    private String SavedPath;

    public String[] getImageInfo() { return new String[]{ImageName, SavedPath};}

    public void ImageSpec(String SvName, String SvPath){
        this.ImageName = SvName;
        this.SavedPath = SvPath;
    }
}
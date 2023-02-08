package com.Don_Spring_Flask.SF_Image.service;

import com.Don_Spring_Flask.SF_Image.domain.FileUpload;
import com.Don_Spring_Flask.SF_Image.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.File;
import java.io.IOException;

@Service("imageService")
public class ImageService {
    @Value("${file.dir}/")
    private String fileDir;

    private final ImageRepo imagerepo;

    @Autowired
    public ImageService(ImageRepo imagerepo) { this.imagerepo = imagerepo; }

    public void saveImage(MultipartFile files) throws IOException
    {
        String name = files.getOriginalFilename();
        String Extension = name.substring(name.lastIndexOf("."));

        if(!Extension.equals(".jpg")) { throw new IOException("확장자 명을 확인해주세요");}

        String rename = "origin";
        rename = rename + Extension;

        FileUpload image = new FileUpload();
        image.ImageSpec(rename, fileDir);
        imagerepo.upload(image);

        files.transferTo(new File(fileDir + rename));
    }
    public void getProcessed(String absPath) throws IOException {
        CommandLine cL = CommandLine.parse("python");
        cL.addArgument("C:/G_Drive/Study/SF_Image/src/main/java/com/Don_Spring_Flask/SF_Image/PythonAI/Image_AI.py");
        cL.addArgument(absPath);

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(cL);
    }
    public void loadImage(String name, String path) { imagerepo.getScaled(name, path); }
    public String[] GetIn() { return imagerepo.find("In").getImageInfo(); }
    public String[] GetOut() { return imagerepo.find("Out").getImageInfo(); }
    public void reset() { imagerepo.remove(); }
}

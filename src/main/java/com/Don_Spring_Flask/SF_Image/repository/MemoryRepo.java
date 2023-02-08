package com.Don_Spring_Flask.SF_Image.repository;
import com.Don_Spring_Flask.SF_Image.domain.FileUpload;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryRepo implements ImageRepo{
    private static final Map<String, FileUpload> store = new HashMap<>();

    @Override
    public void upload(FileUpload image){ store.put("In", image); }

    @Override
    public void getScaled(String name, String path)
    {
        FileUpload seg_image = new FileUpload();
        seg_image.ImageSpec(name, path);
        store.put("Out", seg_image);
    }

    @Override
    public FileUpload find(String code) { return store.get(code); }

    @Override
    public void remove() { store.clear(); }
}

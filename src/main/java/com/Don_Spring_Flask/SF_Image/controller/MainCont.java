package com.Don_Spring_Flask.SF_Image.controller;

import com.Don_Spring_Flask.SF_Image.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainCont {
    private final ImageService imageService;

    public MainCont(ImageService imageService)
    {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @PostMapping("/")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        imageService.saveImage(file);

        String[] imageData = imageService.GetIn();
        imageService.getProcessed(imageData[1] + "/" + imageData[0]);

        String rename = imageData[0].substring(0, imageData[0].lastIndexOf(".")) + "_RE.jpg";
        imageService.loadImage(rename, imageData[1]);

        return "Result";
    }

    @GetMapping("/Result")
    public String result(Model model)
    {
        String[] Origin = imageService.GetIn();
        String[] Regain = imageService.GetOut();
        model.addAttribute("origin", Origin[1] + "/" + Origin[0]);
        model.addAttribute("Computed", Regain[1] + "/" + Regain[0]);
        return "Result";
    }

    @PostMapping("/Result")
    public String retry()
    {
        imageService.reset();
        return "redirect:/index";
    }
}



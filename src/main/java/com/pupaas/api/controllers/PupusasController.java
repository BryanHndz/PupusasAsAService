package com.pupaas.api.controllers;

import com.pupaas.api.services.IS3Service;
import com.pupaas.api.services.impl.IS3ServiceImpl;
import com.pupaas.api.utils.FilenameCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PupusasController {
    @Autowired
    IS3Service s3Service;
    @Autowired
    private IS3ServiceImpl iS3ServiceImpl;

    @GetMapping("/welcome")
    public String getPupusas() {

        return "Welcome to our new Pupusas As A Service API";
    }

    @PostMapping("/upload")
    public String uploadPupusas(@RequestParam("file") MultipartFile file, @RequestParam("masa") int masa, @RequestParam("ing") int ingredient) throws IOException {

        return iS3ServiceImpl.uploadFile(file, masa, ingredient);

    }
}
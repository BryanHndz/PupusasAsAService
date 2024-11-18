package com.pupaas.api.controllers;

import com.pupaas.api.services.IS3Service;
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

    @GetMapping("/welcome")
    public String getPupusas() {

        return "Welcome to our new Pupusas As A Service API";
    }

    @PostMapping("/upload")
    public String uploadPupusas(@RequestParam("file") MultipartFile file) throws IOException {

        return s3Service.uploadFile(file, "dummy filepath");

    }
}
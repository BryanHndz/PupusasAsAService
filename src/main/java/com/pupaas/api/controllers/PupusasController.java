package com.pupaas.api.controllers;

import com.pupaas.api.domain.dtos.UploadPupusaDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTOResponse;
import com.pupaas.api.services.IS3Service;
import com.pupaas.api.services.impl.IS3ServiceImpl;
import com.pupaas.api.utils.FilenameCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    @PostMapping(value = "/upload", consumes = { "application/json", "multipart/form-data" })
    public ResponseEntity<UploadPupusaDTOResponse> uploadPupusas(@Valid @RequestPart("dtoPupusa") UploadPupusaDTO dtoPupusa, @RequestPart("file") MultipartFile image) throws IOException {
        System.out.println(dtoPupusa.toString());
        UploadPupusaDTOResponse response = iS3ServiceImpl.uploadFile(dtoPupusa, image);
        //ResponseEntity myResponseEntity = ResponseEntity.ok(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
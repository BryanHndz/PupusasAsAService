package com.pupaas.api.services.impl;

import com.pupaas.api.services.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class IS3ServiceImpl implements IS3Service {

    @Autowired
    private S3Client s3Client;

    private String bucketName = "bryanhndz-ingsoftware";
    //Cambios propuestos, tener un GlobalErrorHandler y que el controlador devuelva siempre un ResponseEntity.
    public String uploadFile(MultipartFile file, String filepath) throws IOException {


        try{
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "Archivo subido exitosamente. Sos dev, Bryan :D";
        }catch (Exception e) {
            return e.getMessage();
        }
    }
}

package com.pupaas.api.services.impl;

import com.pupaas.api.domain.dtos.UploadPupusaDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTOResponse;
import com.pupaas.api.exceptions.WrongFileUploadingException;
import com.pupaas.api.services.IS3Service;
import com.pupaas.api.utils.FilenameCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
public class IS3ServiceImpl implements IS3Service {

    @Autowired
    private S3Client s3Client;

    @Autowired
    FilenameCreator filenameCreator;

    private String bucketName = "bryanhndz-ingsoftware";
    //Cambios propuestos, tener un GlobalErrorHandler y que el controlador devuelva siempre un ResponseEntity.
    public UploadPupusaDTOResponse uploadFile(UploadPupusaDTO dtoPupusa, MultipartFile image) throws IOException {

        String fileKey = filenameCreator.createFilename(dtoPupusa.getMasa(), dtoPupusa.getIngredientes());
        String filename = image.getOriginalFilename();

        if (!filename.endsWith(".jpg") &&
                !filename.endsWith(".png")  &&
                !filename.endsWith(".gif") &&
                !filename.endsWith(".jpeg")) {
            throw new WrongFileUploadingException(filename);
        }

        try{

            String filePath = fileKey + filename;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();
            String myResponse  = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes())).eTag();
            //String myReponse = "Respuesta dummie";
            return new UploadPupusaDTOResponse("Archivo subido correctamente :D", filePath, myResponse);
        }catch (Exception e) {
            return new UploadPupusaDTOResponse(e.getMessage(), null, null);
        }
    }
}

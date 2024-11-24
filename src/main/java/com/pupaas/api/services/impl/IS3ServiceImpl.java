package com.pupaas.api.services.impl;
import com.pupaas.api.domain.dtos.UploadPupusaDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTOResponse;
import com.pupaas.api.exceptions.WrongFileUploadingException;
import com.pupaas.api.services.IS3Service;
import com.pupaas.api.utils.FilenameCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class IS3ServiceImpl implements IS3Service {

    @Autowired
    private S3Client s3Client;

    @Autowired
    FilenameCreator filenameCreator;

    private String bucketName = "bryanhndz-ingsoftware";

    private final S3Presigner s3Presigner;

    public IS3ServiceImpl() {
        this.s3Presigner = S3Presigner.builder()
                .region(Region.US_EAST_2) // Cambia la región según tu configuración
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

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
            //Uploading the received image to the bucket
            String filePath = fileKey + filename;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();

            String myResponse  = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes())).eTag();

            //Generating a presigned url for uploaded image;
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();

            GetObjectPresignRequest objectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(objectRequest)
                    .build();

            URL presignedUrl = s3Presigner.presignGetObject(objectPresignRequest).url();

            return new UploadPupusaDTOResponse("Archivo subido correctamente :D", presignedUrl.toString(), myResponse);



        }catch (Exception e) {
            return new UploadPupusaDTOResponse(e.getMessage(), null, null);
        }
    }

    public byte[] getOneRandomPupusa() throws IOException {
        //Obteniendo la lista de objetos en el bucket:
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket("bryanhndz-ingsoftware").build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        //Creando la lista de keys de los objetos:
        List<S3Object> objects = listObjectsV2Response.contents();

        //Creando un numero random con el limite superior de la lista anterior;
        Random randomNumber = new Random();
        S3Object randomObject = objects.get(randomNumber.nextInt(objects.size()));

        //Creando request para el objeto y enviándolo al bucket:
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("bryanhndz-ingsoftware")
                .key(randomObject.key())
                .build();

        //Enviando petición a S3 y empaquetando la respuesta:
        try(ResponseInputStream<?> s3Object = s3Client.getObject(getObjectRequest)) {
            byte[] imageBytes = s3Object.readAllBytes();

            return imageBytes;
        }


    }

    public List<byte[]> getManyRandomPupusas(int cantidad) throws IOException {
        //Getting presigned URLs for every JSON.
        return new ArrayList<>();
    }

}



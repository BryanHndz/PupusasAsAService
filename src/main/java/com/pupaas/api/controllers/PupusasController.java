package com.pupaas.api.controllers;

import com.pupaas.api.domain.dtos.ManyPupusasObjectDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTOResponse;
import com.pupaas.api.services.IS3Service;
import com.pupaas.api.services.impl.IS3ServiceImpl;
import com.pupaas.api.utils.FilenameCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class PupusasController {

    @Autowired
    private IS3ServiceImpl iS3ServiceImpl;
    @Autowired
    private FilenameCreator filenameCreator;
    @Autowired
    private S3Client s3Client;

    @GetMapping("/welcome")
    public String getPupusas() {

        return "Welcome to our new Pupusas As A Service API";
    }

    @PostMapping(value = "/upload", consumes = { "multipart/form-data" })
    public ResponseEntity<UploadPupusaDTOResponse> uploadPupusas(@Valid @RequestPart("dtoPupusa") UploadPupusaDTO dtoPupusa,@NotNull @RequestPart("file") MultipartFile image) throws IOException {

            UploadPupusaDTOResponse response = iS3ServiceImpl.uploadFile(dtoPupusa, image);
        //ResponseEntity myResponseEntity = ResponseEntity.ok(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Crear controlador tipo GET para recibir entre 1 y 10 imagenes random de pupusas
    //Si no se envian parametros al controlador, por defecto sera una sola imagen de masa random e ingredientes random
    @GetMapping("/pupusa")
    public ResponseEntity<byte[]> getOnePupusasImage() throws IOException {

            byte[] imageBytes = iS3ServiceImpl.getOneRandomPupusa();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","image/jpeg");

            return new ResponseEntity<>(imageBytes,headers,HttpStatus.OK);
    }

    @GetMapping("/pupusa/many")
    public ResponseEntity<List<ManyPupusasObjectDTO>> getManyPupusasImage(@RequestParam("cantidad") int cantidad) throws IOException, IllegalArgumentException {
        List<ManyPupusasObjectDTO> myListOfObjects;

        if (cantidad <= 0 || cantidad > 10) {
            throw new IllegalArgumentException("El cantidad debe ser mayor que 0 y menor que 10");
        }

        myListOfObjects = iS3ServiceImpl.getManyRandomPupusas(cantidad);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        return new ResponseEntity<>(myListOfObjects,headers,HttpStatus.OK);
    }

    @GetMapping("/pupusa/custom/{masa}/{ingrediente}")
    public ResponseEntity<byte[]> getOneCustomPupusas(@PathVariable int masa, @PathVariable int ingrediente) throws IOException {
        if (masa <= 0 || masa > 2) {
            throw new IllegalArgumentException("Los valores ingresados para la masa deben ser 1 para Arroz y 2 para Maiz");
        }
        if (ingrediente <= 0 || ingrediente > 3) {
            throw new IllegalArgumentException("El valor del ingrediente debe ser 1 para Frijol-queso, 2 para Revueltas y 3 para Queso");
        }

        byte[] imageBytes = iS3ServiceImpl.getOneCustomPupusa(masa, ingrediente);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","image/jpeg");
        return new ResponseEntity<>(imageBytes,headers,HttpStatus.OK);

    }

    @GetMapping("/pupusa/list")
    public ResponseEntity<List<String>> listAllKeys(){
        List<String> keys = iS3ServiceImpl.listPupusasKeys();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        return new ResponseEntity(keys,headers,HttpStatus.OK);
    }

    @DeleteMapping("/pupusa/delete")
    public ResponseEntity<String> deletePupusa(@RequestParam("key") String fileKey) throws IOException {
            String deletingMessage = iS3ServiceImpl.deletePupusasByKey(fileKey);

            return new ResponseEntity<>(deletingMessage,HttpStatus.OK);
    }

}
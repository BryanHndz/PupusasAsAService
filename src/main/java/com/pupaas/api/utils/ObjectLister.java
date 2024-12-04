package com.pupaas.api.utils;

import com.pupaas.api.domain.dtos.ManyPupusasObjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectLister {

    @Autowired
    private S3Client s3Client;

    public List<S3Object> listObjects(String bucketName){
        ArrayList<ManyPupusasObjectDTO> pupusasList = new ArrayList<>();

        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3Object> objects = listObjectsV2Response.contents();

        List<S3Object> filteredObjects = objects.stream().filter(o -> o.key().endsWith(".jpg")
        || o.key().endsWith(".png")
        || o.key().endsWith(".gif")
        || o.key().endsWith(".jpeg")).collect(Collectors.toList());

        return filteredObjects;
    }
}
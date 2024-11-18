package com.pupaas.api.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
    public String uploadFile(MultipartFile file,String filepath) throws IOException;

}

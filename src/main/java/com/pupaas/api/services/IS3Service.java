package com.pupaas.api.services;

import com.pupaas.api.domain.dtos.UploadPupusaDTO;
import com.pupaas.api.domain.dtos.UploadPupusaDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
    public UploadPupusaDTOResponse uploadFile(UploadPupusaDTO uploadPupusaDTO, MultipartFile image) throws IOException;

}

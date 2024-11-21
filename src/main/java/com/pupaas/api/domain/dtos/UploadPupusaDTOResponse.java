package com.pupaas.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UploadPupusaDTOResponse {
    private String message;
    private String filePath;
    private String identificador;


}

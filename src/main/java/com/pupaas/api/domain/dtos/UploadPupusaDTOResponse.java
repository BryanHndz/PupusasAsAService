package com.pupaas.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadPupusaDTOResponse {
    private String message;
    private String fileUrl;
    private String identificador;


}

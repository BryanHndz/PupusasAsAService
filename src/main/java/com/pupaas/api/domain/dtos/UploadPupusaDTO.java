package com.pupaas.api.domain.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadPupusaDTO {
    @NotNull
    @Min(1)
    @Max(2)
    private int masa;

    @NotNull
    @Min(1)
    @Max(3)
    private int ingredientes;
}

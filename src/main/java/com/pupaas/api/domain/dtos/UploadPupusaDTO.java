package com.pupaas.api.domain.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UploadPupusaDTO {
    @NotNull
    @Size(min = 1, max = 2,message = "Solo puedes ingresar '1' para arroz o '2' para maíz")
    private int masa;
    @Size(min = 1, max = 3,message = "Solo puedes ingresar '1' para Frijol-Queso, '2' para Revueltas o '3' para Queso")
    private int ingredientes;
}

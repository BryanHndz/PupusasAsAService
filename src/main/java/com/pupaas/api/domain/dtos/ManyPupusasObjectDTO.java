package com.pupaas.api.domain.dtos;

import lombok.Data;

import java.net.URL;

@Data
public class ManyPupusasObjectDTO {
    private String masa;
    private String ingrediente;
    private URL imageUrl;
}

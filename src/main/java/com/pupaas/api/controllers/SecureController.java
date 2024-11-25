package com.pupaas.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class SecureController {

    @GetMapping("/secure-data")
    public String getSecureData(Principal principal) {
        return "Esta es una respuesta protegida. Usuario autenticado: " + principal.getName();
    }
}


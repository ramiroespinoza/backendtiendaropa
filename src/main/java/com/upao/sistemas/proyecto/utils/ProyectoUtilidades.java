package com.upao.sistemas.proyecto.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProyectoUtilidades {

    private ProyectoUtilidades() {

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);

    }

}

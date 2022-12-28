package com.upao.sistemas.proyecto.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProductoServicio {

    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);


}

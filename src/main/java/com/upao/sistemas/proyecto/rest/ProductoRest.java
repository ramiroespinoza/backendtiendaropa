package com.upao.sistemas.proyecto.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductoRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNerProduct(@RequestBody Map<String, String> requestMap);

}

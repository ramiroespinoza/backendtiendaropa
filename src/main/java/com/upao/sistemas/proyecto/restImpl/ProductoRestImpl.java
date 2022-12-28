package com.upao.sistemas.proyecto.restImpl;

import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.rest.ProductoRest;
import com.upao.sistemas.proyecto.service.ProductoServicio;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProductoRestImpl implements ProductoRest {
    @Autowired
    ProductoServicio productService;

    @Override
    public ResponseEntity<String> addNerProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

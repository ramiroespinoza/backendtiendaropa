package com.upao.sistemas.proyecto.restImpl;

import com.upao.sistemas.proyecto.POJO.Categoria;
import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.rest.CategoriaRest;
import com.upao.sistemas.proyecto.service.CategoriaServicio;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoriaRestImpl implements CategoriaRest {

    @Autowired
    CategoriaServicio categoriaServicio;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoriaServicio.addNewCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategory(String filterValue) {
        try{
            return categoriaServicio.getAllCategory(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoriaServicio.updateCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

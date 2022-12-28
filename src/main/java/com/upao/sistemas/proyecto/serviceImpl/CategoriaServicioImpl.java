package com.upao.sistemas.proyecto.serviceImpl;

import com.upao.sistemas.proyecto.JWT.JwtFiltro;
import com.upao.sistemas.proyecto.POJO.Categoria;
import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.dao.CategoriaDao;
import com.upao.sistemas.proyecto.service.CategoriaServicio;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaServicioImpl implements CategoriaServicio {

    @Autowired
    CategoriaDao categoriaDao;

    @Autowired
    JwtFiltro jwtFiltro;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if (jwtFiltro.isAdmin()){
                if (validateCategoryMap(requestMap,false)){
                    categoriaDao.save(getCategoryFromMap(requestMap, false));
                    return ProyectoUtilidades.getResponseEntity("Category Added Succesfully", HttpStatus.OK);
                }
            }else{
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED );
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR );
    }



    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId){
        if(requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId) {
                return true;
            }
        }
        return false;
    }


    private Categoria getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Categoria category = new Categoria();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Categoria>> getAllCategory(String filterValue) {
        return new ResponseEntity<List<Categoria>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if (jwtFiltro.isAdmin()){
                if (validateCategoryMap(requestMap, true)){
                    Optional optional= categoriaDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoriaDao.save(getCategoryFromMap(requestMap, true));
                        return ProyectoUtilidades.getResponseEntity("Category update succesfully", HttpStatus.OK);
                    }else{
                        return ProyectoUtilidades.getResponseEntity("Category id does not exist", HttpStatus.OK);
                    }
                }
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else{
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

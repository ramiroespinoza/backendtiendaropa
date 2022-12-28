package com.upao.sistemas.proyecto.serviceImpl;

import com.upao.sistemas.proyecto.JWT.JwtFiltro;
import com.upao.sistemas.proyecto.POJO.Categoria;
import com.upao.sistemas.proyecto.POJO.Producto;
import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.dao.ProductoDao;
import com.upao.sistemas.proyecto.service.ProductoServicio;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    ProductoDao productoDao;

    @Autowired
    JwtFiltro jwtFiltro;


    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if (jwtFiltro.isAdmin()){
                if (validateProductMap(requestMap, false)){
                    productoDao.save(getProductoFromMap(requestMap, false));
                    return ProyectoUtilidades.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;

    }
    private Producto getProductoFromMap(Map<String, String> requestMap, boolean isAdd) {
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(requestMap.get("categoryId")));


        Producto producto = new Producto();
        if (isAdd){
            producto.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            producto.setStatus("true");
        }

        producto.setCategoria(categoria);
        producto.setName(requestMap.get("name"));
        producto.setDescription(requestMap.get("description"));
        producto.setPrice(Integer.parseInt(requestMap.get("price")));
        return producto;
    }
}

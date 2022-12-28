package com.upao.sistemas.proyecto.restImpl;

import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.rest.UsuarioRest;
import com.upao.sistemas.proyecto.service.UsuarioServicio;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import com.upao.sistemas.proyecto.wrapper.UsuarioWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioRestImpl implements UsuarioRest {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            return usuarioServicio.signUp(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return usuarioServicio.login(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UsuarioWrapper>> getAllUser() {
        try{
            return usuarioServicio.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UsuarioWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return usuarioServicio.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return usuarioServicio.checkToken();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return usuarioServicio.changePassword(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return usuarioServicio.forgotPassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

package com.upao.sistemas.proyecto.serviceImpl;

import com.google.common.base.Strings;
import com.upao.sistemas.proyecto.JWT.JwtFiltro;
import com.upao.sistemas.proyecto.JWT.JwtUtil;
import com.upao.sistemas.proyecto.JWT.ServicioDetalleUsuariosCliente;
import com.upao.sistemas.proyecto.POJO.Usuario;
import com.upao.sistemas.proyecto.constents.ProyectoConstantes;
import com.upao.sistemas.proyecto.dao.UsuarioDao;
import com.upao.sistemas.proyecto.service.UsuarioServicio;
import com.upao.sistemas.proyecto.utils.CorreoUtilidades;
import com.upao.sistemas.proyecto.utils.ProyectoUtilidades;
import com.upao.sistemas.proyecto.wrapper.UsuarioWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ServicioDetalleUsuariosCliente servicioDetalleUsuariosCliente;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFiltro jwtFiltro;

    @Autowired
    CorreoUtilidades correoUtilidades;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                Usuario usuario = usuarioDao.findByEmailId(requestMap.get("Correo"));
                if (Objects.isNull(usuario)) {
                    usuarioDao.save(getUserFromMap(requestMap));
                    return ProyectoUtilidades.getResponseEntity("Registrado exitosamente.", HttpStatus.OK);
                } else {
                    return ProyectoUtilidades.getResponseEntity("Correo ya existe.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("Nombre") && requestMap.containsKey("NumeroTelefonico")
                && requestMap.containsKey("Correo") && requestMap.containsKey("Contraseña")) {

            return true;
        }
        return false;
    }

    private Usuario getUserFromMap(Map<String, String> requestMap){
        Usuario usuario = new Usuario();
        usuario.setName(requestMap.get("Nombre"));
        usuario.setContactNumber(requestMap.get("NumeroTelefonico"));
        usuario.setEmail(requestMap.get("Correo"));
        usuario.setPassword(requestMap.get("Contraseña"));
        usuario.setStatus("false");
        usuario.setRole("usuario");
        return usuario;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("Correo"),requestMap.get("Contraseña"))
            );
            if (auth.isAuthenticated()){
                if(servicioDetalleUsuariosCliente.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(servicioDetalleUsuariosCliente.getUserDetail().getEmail(),
                                    servicioDetalleUsuariosCliente.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UsuarioWrapper>> getAllUser() {
        try{
            if(jwtFiltro.isAdmin()){
                return new ResponseEntity<>(usuarioDao.getAllUser(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if(jwtFiltro.isAdmin()){
                Optional<Usuario> optional = usuarioDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    usuarioDao.updateStatus(requestMap.get("Estado"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("Estado"),optional.get().getEmail(),usuarioDao.getAllAdmin());
                    return ProyectoUtilidades.getResponseEntity("Estado del usuario, actualizado exitosamente", HttpStatus.OK);
                }
                else{
                    return ProyectoUtilidades.getResponseEntity("ID del usuario, no existe", HttpStatus.OK);
                }
            } else {
                return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String usuario, List<String> allAdmin) {
        allAdmin.remove(jwtFiltro.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")){
            correoUtilidades.sendSimpleMessage(jwtFiltro.getCurrentUser(),"Account Approved","USER:- " + usuario + " \n is approved by \nADMIN:-" + jwtFiltro.getCurrentUser(), allAdmin);
        } else {
            correoUtilidades.sendSimpleMessage(jwtFiltro.getCurrentUser(),"Account Disabled","USER:- " + usuario + " \n is disabled by \nADMIN:-" + jwtFiltro.getCurrentUser(), allAdmin);

        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return ProyectoUtilidades.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            Usuario userObj = usuarioDao.findByEmail(jwtFiltro.getCurrentUser());
            if(!userObj.equals(null)) {
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    usuarioDao.save(userObj);
                    return ProyectoUtilidades.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return ProyectoUtilidades.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            Usuario usuario = usuarioDao.findByEmail(requestMap.get("Correo"));
            if(!Objects.isNull(usuario) && !Strings.isNullOrEmpty(usuario.getEmail()))
                correoUtilidades.forgotMail(usuario.getEmail(), "Credentials by Login System", usuario.getPassword());
            return ProyectoUtilidades.getResponseEntity("Check your mail for Credentials.", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProyectoUtilidades.getResponseEntity(ProyectoConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

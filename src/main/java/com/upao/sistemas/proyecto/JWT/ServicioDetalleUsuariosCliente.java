package com.upao.sistemas.proyecto.JWT;

import com.upao.sistemas.proyecto.dao.UsuarioDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class ServicioDetalleUsuariosCliente implements UserDetailsService {

    @Autowired
    UsuarioDao usuarioDao;

    private com.upao.sistemas.proyecto.POJO.Usuario userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername{}", username);
        userDetail = usuarioDao.findByEmailId(username);
        if(!Objects.isNull(userDetail))
            return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
        else
            throw new UsernameNotFoundException("Usuario No Encontrado.");
    }

    public com.upao.sistemas.proyecto.POJO.Usuario getUserDetail(){
        return userDetail;
    }
}

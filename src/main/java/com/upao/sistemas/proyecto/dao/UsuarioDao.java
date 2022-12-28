package com.upao.sistemas.proyecto.dao;

import com.upao.sistemas.proyecto.POJO.Usuario;
import com.upao.sistemas.proyecto.wrapper.UsuarioWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {


    @Query(name = "User.findByEmailId")
    Usuario findByEmailId(@Param("Correo") String email);

    @Query(name = "User.getAllUser")
    List<UsuarioWrapper> getAllUser();

    @Query(name = "User.getAllAdmin")
    List<String> getAllAdmin();

    @Transactional
    @Modifying
    @Query(name = "User.updateStatus")
    Integer updateStatus(@Param("status") String status,@Param("id") Integer id);

    Usuario findByEmail(String email);
}


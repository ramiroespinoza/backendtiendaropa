package com.upao.sistemas.proyecto.dao;

import com.upao.sistemas.proyecto.POJO.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<Producto, Integer> {
}

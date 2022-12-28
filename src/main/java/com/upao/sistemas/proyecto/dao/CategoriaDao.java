package com.upao.sistemas.proyecto.dao;

import com.upao.sistemas.proyecto.POJO.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoriaDao extends JpaRepository<Categoria, Integer> {

    @Query(name = "Category.getALLCategory")
    List<Categoria> getAllCategory();

}

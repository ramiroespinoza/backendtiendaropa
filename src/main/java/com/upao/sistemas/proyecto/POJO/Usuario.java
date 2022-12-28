package com.upao.sistemas.proyecto.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from Usuario u where u.email=:email")

@NamedQuery(name = "User.getAllUser",query = "select new com.upao.sistemas.proyecto.wrapper.UsuarioWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from Usuario u where u.role='user'")

@NamedQuery(name = "User.updateStatus",query = "update Usuario u set u.status=:status where u.id=:id")

@NamedQuery(name = "User.getAllAdmin",query = "select u.email from Usuario u where u.role='admin'")


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nombre")
    private String name;

    @Column(name = "NumeroTelefonico")
    private String contactNumber;

    @Column(name = "Correo")
    private String email;

    @Column(name = "Contraseña")
    private String password;

    @Column(name = "Estado")
    private String status;

    @Column(name = "Rol")
    private String role;

}

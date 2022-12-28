package com.upao.sistemas.proyecto.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioWrapper {

    private Integer id;

    private String name;

    private String email;

    private String contactNumber;

    private String status;

    public UsuarioWrapper(Integer id, String name, String email, String contactNumber, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.status = status;
    }
}

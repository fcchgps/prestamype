package com.prestamype.factura.infraestructure.adapter.entity;



import javax.persistence.*;
@Entity
public class UsuarioRolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioRolId;

    @ManyToOne(fetch = FetchType.EAGER)
    private UsuarioEntity usuario;

    @ManyToOne
    private RolEntity rol;

    public Long getUsuarioRolId() {

        return usuarioRolId;
    }

    public void setUsuarioRolId(Long usuarioRolId) {

        this.usuarioRolId = usuarioRolId;
    }

    public UsuarioEntity getUsuario() {

        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {

        this.usuario = usuario;
    }

    public RolEntity getRol() {

        return rol;
    }

    public void setRol(RolEntity rol) {

        this.rol = rol;
    }
}

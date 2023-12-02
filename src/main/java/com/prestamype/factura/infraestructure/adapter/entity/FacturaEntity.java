package com.prestamype.factura.infraestructure.adapter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Table(name = "factura")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El Codigo no puede ser Nulo")
    private String codigo;

    @Column(name = "fechaemision")
    @NotNull(message = "La Fecha Emision no puede ser Nulo")
    private String fechaEmision;
    @NotNull(message = "El Monto no puede ser Nulo")
    private Double monto;
    @NotNull(message = "La Moneda  no puede ser Nulo")
    private String moneda;
    @NotNull(message = "El Ruc Proveedor no puede ser Nulo")
    @Column(name = "rucproveedor")
    private String rucProveedor;
    @NotNull(message = "El Ruc Emisor no puede ser Nulo")
    @Column(name = "rucemisor")
    private String rucEmisor;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "creaciondate",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creaciondate;

}

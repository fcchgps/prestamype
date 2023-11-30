package com.prestamype.factura.infraestructure.adapter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "financiamiento")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanciamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
 //   @JoinColumn(name = "idfactura", nullable = false)
  //  private FacturaEntity facturaEntity;


    private String estado;
    @Column(name = "fecharegistro")
    private String fechaRegistro;
    @Column(name = "fechapago")
    private String fechaPago;

}

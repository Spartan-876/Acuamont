package com.example.acceso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cuota", nullable = false)
    private Cuota cuota;

    @Column(nullable = false)
    private BigDecimal montoPagado;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false,length = 250)
    private String comentario;

    @Column(nullable = false)
    private Integer estado = 1; // 1 = Activo, 0 = Inactivo

}

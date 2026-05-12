package com.example.acceso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "ajustes_inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @NotNull
    @Column(nullable = false)
    private Integer cantidad;

    @Size(max = 250, message = "El comentario debe tener máximo 250 caracteres")
    private String comentario;

}

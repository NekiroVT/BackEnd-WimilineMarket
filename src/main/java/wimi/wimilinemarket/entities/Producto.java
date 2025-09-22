package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @Column(name = "producto_id", columnDefinition = "RAW(16)")
    private UUID productoId;

    // FK → Categoria.categoria_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(length = 150)
    private String nombre;

    @Lob // para TEXT
    private String descripcion;

    @Column(length = 150)
    private String detalles;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    private Integer stock;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    private Boolean activo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

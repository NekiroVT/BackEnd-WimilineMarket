package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "carrito")
@Data
public class Carrito {

    @Id
    @Column(name = "carrito_id", columnDefinition = "RAW(16)")
    private UUID carritoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "total_precio", precision = 10, scale = 2)
    private BigDecimal totalPrecio;

    @Column(name = "total_cantidad")
    private Integer totalCantidad;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Este método solo actualiza el precio y la cantidad
    public void actualizarTotales(BigDecimal precioTotal, int cantidadTotal) {
        this.totalPrecio = precioTotal;
        this.totalCantidad = cantidadTotal;
    }





}

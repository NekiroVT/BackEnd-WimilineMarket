package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    // Relación uno a muchos con CarritoItem
    @OneToMany(mappedBy = "carrito", fetch = FetchType.LAZY)
    private List<CarritoItem> carritoItems; // Lista de productos en el carrito

    // Este método solo actualiza el precio y la cantidad
    public void actualizarTotales(BigDecimal precioTotal, int cantidadTotal) {
        this.totalPrecio = precioTotal;
        this.totalCantidad = cantidadTotal;
    }

    // Método getter para obtener los CarritoItems (productos en el carrito)
    public List<CarritoItem> getCarritoItems() {
        return carritoItems;
    }
}

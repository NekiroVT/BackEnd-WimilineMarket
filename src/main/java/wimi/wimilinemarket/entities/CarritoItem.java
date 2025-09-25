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
@Table(name = "carrito_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @Column(name = "carrito_item_id", columnDefinition = "RAW(16)")
    private UUID carritoItemId;

    @Column(name = "cantidad")
    private Integer cantidad; // Cantidad del producto en el carrito

    @CreationTimestamp
    @Column(name = "agregado_at")
    private LocalDateTime agregadoAt; // Fecha y hora de adición al carrito

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id") // FK hacia Carrito
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id") // FK hacia Producto
    private Producto producto;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Fecha de creación del item en el carrito

    @Column(name = "estado", nullable = false)
    private Boolean estado; // Estado del item en el carrito (activo/inactivo)

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario; // Precio unitario del producto en el carrito

    // Método setter para precioUnitario
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}

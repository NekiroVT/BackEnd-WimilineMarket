package wimi.wimilinemarket.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "CarritoItem")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoItem {

    @Id
    @Column(name = "carrito_item_id", columnDefinition = "RAW(16)")
    private UUID carritoItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", referencedColumnName = "carrito_id")
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    private Producto producto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @CreationTimestamp
    @Column(name = "agregado_at", updatable = false)
    private LocalDateTime agregadoAt;
}

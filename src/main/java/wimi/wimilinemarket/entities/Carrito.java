package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @Column(name = "carrito_id", columnDefinition = "RAW(16)")
    private UUID carritoId;

    // FK → Usuario.usuario_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")   // columna FK en la tabla Carrito
    private Usuario usuario;

    @Column(length = 50)
    private String estado;             // ABIERTO, CONVERTIDO, etc.

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

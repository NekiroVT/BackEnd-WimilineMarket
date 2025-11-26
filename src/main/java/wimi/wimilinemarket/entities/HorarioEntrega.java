package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "horario_entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioEntrega {

    @Id
    @Column(name = "entrega_id", columnDefinition = "RAW(16)")
    private UUID entregaId;

    @Column(name = "hora_programada")
    private LocalDateTime horaProgramada;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

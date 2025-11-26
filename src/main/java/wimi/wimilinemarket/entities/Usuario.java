package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "usuario_id", columnDefinition = "RAW(16)")
    private UUID usuarioId;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 150)
    private String email;

    @Column(length = 1000)
    private String fotoUrl;

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String telefono;

    private Boolean activo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // RELACIÓN: 1 Usuario -> N Direcciones
    @OneToMany(
            mappedBy = "usuario",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Direccion> direcciones;

    public Usuario(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}

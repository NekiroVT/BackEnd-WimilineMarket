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

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String telefono;

    private Boolean activo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Relación OneToMany con Direccion
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Direccion> direcciones;

    // Constructor que acepta un UUID
    public Usuario(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}

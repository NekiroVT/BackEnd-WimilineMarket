package wimi.wimilinemarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @Column(name = "direccion_id", columnDefinition = "RAW(16)")
    private UUID direccionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    @Column(name = "direccion_texto", length = 255)
    private String direccionTexto;

    @Column(length = 255)
    private String referencia;

    @Column(length = 100)
    private String destinatario;

    @Column(length = 100)
    private String distrito;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 10)
    private String codigo_postal;
}

package wimi.wimilinemarket.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UsuarioDTO {

    private UUID usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;
    private LocalDateTime createdAt;

    private String password;

    public UsuarioDTO() {}

    public UsuarioDTO(UUID usuarioId, String nombre, String apellido, String email,
                      String telefono, Boolean activo, LocalDateTime createdAt) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
        this.createdAt = createdAt;
    }

    public UsuarioDTO(UUID usuarioId, String nombre, String apellido, String email,
                      String telefono, Boolean activo, LocalDateTime createdAt, String password) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
        this.createdAt = createdAt;
        this.password = password;
    }
}

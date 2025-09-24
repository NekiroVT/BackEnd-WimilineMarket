package wimi.wimilinemarket.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CategoriaDTO {

    private UUID categoriaId;
    private String nombre;
    private String descripcion;
    private Boolean activa;
    private LocalDateTime createdAt;

    // Constructor vacío
    public CategoriaDTO() {}

    // Constructor con todos los campos
    public CategoriaDTO(UUID categoriaId, String nombre, String descripcion, Boolean activa, LocalDateTime createdAt) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
        this.createdAt = createdAt;
    }
}

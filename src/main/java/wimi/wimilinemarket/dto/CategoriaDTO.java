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


    private String imagenUrl;


    public CategoriaDTO() {}


    public CategoriaDTO(UUID categoriaId, String nombre, String descripcion, Boolean activa, LocalDateTime createdAt, String imagenUrl) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
        this.createdAt = createdAt;
        this.imagenUrl = imagenUrl;
    }
}

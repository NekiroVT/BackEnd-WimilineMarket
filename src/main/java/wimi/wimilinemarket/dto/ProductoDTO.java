package wimi.wimilinemarket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductoDTO {

    private UUID productoId;
    private UUID categoriaId; // Foreign Key a Categoria
    private String nombre;
    private String descripcion;
    private String detalles;
    private BigDecimal precio;
    private Integer stock;
    private String imagenUrl;
    private Boolean activo;
    private LocalDateTime createdAt;

    // Constructor vacío
    public ProductoDTO() {}

    // Constructor con todos los campos
    public ProductoDTO(UUID productoId, UUID categoriaId, String nombre, String descripcion, String detalles,
                       BigDecimal precio, Integer stock, String imagenUrl, Boolean activo, LocalDateTime createdAt) {
        this.productoId = productoId;
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.detalles = detalles;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.activo = activo;
        this.createdAt = createdAt;
    }
}

package wimi.wimilinemarket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarritoDTO {
    private UUID carritoId;          // ID del carrito
    private UUID usuarioId;          // ID del usuario asociado al carrito
    private BigDecimal totalPrecio;  // Precio total de todos los productos en el carrito
    private int totalCantidad;       // Total de productos (cantidad) en el carrito
}

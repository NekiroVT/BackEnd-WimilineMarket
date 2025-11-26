package wimi.wimilinemarket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarritoDTO {
    private UUID carritoId;
    private UUID usuarioId;
    private BigDecimal totalPrecio;
    private int totalCantidad;
}

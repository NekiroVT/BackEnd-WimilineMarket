package wimi.wimilinemarket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarritoItemDTO {
    private UUID carritoItemId;
    private UUID carritoId;
    private UUID productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
    private Boolean estado;
}

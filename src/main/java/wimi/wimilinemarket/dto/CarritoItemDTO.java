package wimi.wimilinemarket.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarritoItemDTO {
    private UUID carritoItemId;      // ID del item en el carrito
    private UUID carritoId;          // ID del carrito al que pertenece el item
    private UUID productoId;         // ID del producto agregado
    private int cantidad;            // Cantidad de ese producto en el carrito
    private BigDecimal precioUnitario; // Precio unitario del producto
    private Boolean estado;          // Estado del item en el carrito (activo/inactivo)
}

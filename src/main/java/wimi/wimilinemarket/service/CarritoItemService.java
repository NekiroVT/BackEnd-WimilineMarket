// CarritoItemService.java - Servicio responsable de manipular los productos en el carrito
package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.CarritoItemDTO;
import java.util.UUID;

public interface CarritoItemService {
    CarritoItemDTO agregarProductoAlCarrito(UUID usuarioId, UUID productoId, int cantidad);
    void actualizarCantidadProducto(UUID usuarioId, UUID productoId, int cantidad);
    void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId);
}

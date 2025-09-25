package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.CarritoItemDTO;  // Asegúrate de importar el DTO adecuado

import java.util.UUID;

public interface CarritoItemService {
    // Agregar un producto al carrito
    CarritoItemDTO agregarProductoAlCarrito(UUID usuarioId, UUID productoId, int cantidad);

    // Actualizar la cantidad de un producto en el carrito
    void actualizarCantidadProducto(UUID usuarioId, UUID productoId, int cantidad);

    // Eliminar un producto del carrito
    void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId);
}

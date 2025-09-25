package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.CarritoItemDTO;  // Importar CarritoItemDTO
import wimi.wimilinemarket.entities.Carrito;

import java.util.UUID;

public interface CarritoService {

    CarritoItemDTO agregarProductoAlCarrito(UUID usuarioId, UUID productoId, int cantidad);  // El tipo de retorno debe ser CarritoItemDTO
    Carrito obtenerCarritoPorUsuario(UUID usuarioId);
    void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId);
}

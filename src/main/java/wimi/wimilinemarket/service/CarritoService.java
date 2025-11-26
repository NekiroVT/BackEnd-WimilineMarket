package wimi.wimilinemarket.service;

import wimi.wimilinemarket.entities.Carrito;
import java.util.UUID;

public interface CarritoService {
    Carrito obtenerCarritoPorUsuario(UUID usuarioId);
    void recalcularTotalesCarrito(UUID carritoId);
}

package wimi.wimilinemarket.repository;

import wimi.wimilinemarket.entities.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, UUID> {
    // Buscar un item en el carrito según el usuario y el producto
    CarritoItem findByCarrito_Usuario_UsuarioIdAndProducto_ProductoId(UUID usuarioId, UUID productoId);
    List<CarritoItem> findByCarrito_CarritoId(UUID carritoId);

}

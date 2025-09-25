package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.dto.CarritoItemDTO;
import wimi.wimilinemarket.entities.Carrito;
import wimi.wimilinemarket.entities.CarritoItem;
import wimi.wimilinemarket.entities.Producto;
import wimi.wimilinemarket.entities.Usuario;
import wimi.wimilinemarket.repository.CarritoItemRepository;
import wimi.wimilinemarket.repository.CarritoRepository;
import wimi.wimilinemarket.repository.ProductoRepository;
import wimi.wimilinemarket.service.CarritoItemService;
import wimi.wimilinemarket.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    private final CarritoItemRepository carritoItemRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final CarritoService carritoService;

    @Autowired
    public CarritoItemServiceImpl(CarritoItemRepository carritoItemRepository, CarritoRepository carritoRepository,
                                  ProductoRepository productoRepository, CarritoService carritoService) {
        this.carritoItemRepository = carritoItemRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
        this.carritoService = carritoService;
    }

    @Override
    public CarritoItemDTO agregarProductoAlCarrito(UUID usuarioId, UUID productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Obtener o crear el carrito del usuario
        Carrito carrito = carritoRepository.findByUsuario_UsuarioIdAndEstadoTrue(usuarioId);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(new Usuario(usuarioId)); // Asociamos el carrito al usuario
            carrito.setEstado(true);  // Estado activo por defecto
            carritoRepository.save(carrito);  // Guardar el carrito
        }

        // Verificar si el producto ya existe en el carrito
        CarritoItem carritoItem = carritoItemRepository.findByCarrito_Usuario_UsuarioIdAndProducto_ProductoId(usuarioId, productoId);
        if (carritoItem == null) {
            // Agregar producto al carrito
            carritoItem = new CarritoItem();
            carritoItem.setCarrito(carrito);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(cantidad);
            carritoItem.setPrecioUnitario(producto.getPrecio());
            carritoItem.setEstado(true); // El item está activo
            carritoItemRepository.save(carritoItem);
        } else {
            // Si ya existe en el carrito, actualizar cantidad
            if (producto.getStock() < cantidad) {
                carritoItem.setCantidad(producto.getStock()); // Limitar al stock disponible
                carritoItem.setEstado(false);  // Marcar como agotado
            } else {
                carritoItem.setCantidad(cantidad);
                carritoItem.setEstado(true); // El item sigue activo
            }
            carritoItemRepository.save(carritoItem);
        }

        // Recalcular totales del carrito
        carritoService.recalcularTotalesCarrito(carrito.getCarritoId());

        // Mapear a DTO
        CarritoItemDTO carritoItemDTO = new CarritoItemDTO();
        carritoItemDTO.setCarritoItemId(carritoItem.getCarritoItemId());
        carritoItemDTO.setCarritoId(carrito.getCarritoId());
        carritoItemDTO.setProductoId(productoId);
        carritoItemDTO.setCantidad(carritoItem.getCantidad());
        carritoItemDTO.setPrecioUnitario(producto.getPrecio());
        carritoItemDTO.setEstado(carritoItem.getEstado());

        return carritoItemDTO;
    }

    @Override
    public void actualizarCantidadProducto(UUID usuarioId, UUID productoId, int cantidad) {
        // Lógica de actualización de cantidad de productos
    }

    @Override
    public void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId) {
        // Lógica de eliminación de productos del carrito
    }
}

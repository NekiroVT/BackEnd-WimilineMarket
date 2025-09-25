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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    private final CarritoItemRepository carritoItemRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public CarritoItemServiceImpl(CarritoItemRepository carritoItemRepository, CarritoRepository carritoRepository, ProductoRepository productoRepository) {
        this.carritoItemRepository = carritoItemRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public CarritoItemDTO agregarProductoAlCarrito(UUID usuarioId, UUID productoId, int cantidad) {
        // Validar que el producto exista
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Obtener el carrito del usuario
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
            // Si el producto no está en el carrito, agregarlo
            carritoItem = new CarritoItem();
            carritoItem.setCarrito(carrito);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(cantidad);
            carritoItem.setPrecioUnitario(producto.getPrecio());
            carritoItem.setEstado(true); // El item está activo
            carritoItemRepository.save(carritoItem);
        } else {
            // Si el producto ya está en el carrito, solo actualizar la cantidad
            carritoItem.setCantidad(carritoItem.getCantidad() + cantidad);
            carritoItemRepository.save(carritoItem);
        }

        // Recalcular totales del carrito después de agregar el item
        BigDecimal precioTotal = BigDecimal.ZERO;
        int cantidadTotal = 0;

        // Obtener todos los items del carrito
        for (CarritoItem item : carritoItemRepository.findByCarrito_CarritoId(carrito.getCarritoId())) {
            precioTotal = precioTotal.add(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
            cantidadTotal += item.getCantidad();
        }

        // Llamar al método actualizarTotales
        carrito.actualizarTotales(precioTotal, cantidadTotal);
        carritoRepository.save(carrito); // Guardamos después de actualizar totales

        // Mapear el item agregado a DTO
        CarritoItemDTO carritoItemDTO = new CarritoItemDTO();
        carritoItemDTO.setCarritoItemId(carritoItem.getCarritoItemId());
        carritoItemDTO.setCarritoId(carrito.getCarritoId());
        carritoItemDTO.setProductoId(productoId);
        carritoItemDTO.setCantidad(carritoItem.getCantidad());
        carritoItemDTO.setPrecioUnitario(producto.getPrecio());
        carritoItemDTO.setEstado(carritoItem.getEstado());

        return carritoItemDTO;  // Devolver el DTO
    }

    @Override
    public void actualizarCantidadProducto(UUID usuarioId, UUID productoId, int cantidad) {
        // Verificar si el producto existe en el carrito
        CarritoItem carritoItem = carritoItemRepository.findByCarrito_Usuario_UsuarioIdAndProducto_ProductoId(usuarioId, productoId);
        if (carritoItem != null) {
            carritoItem.setCantidad(cantidad);  // Actualizamos la cantidad
            carritoItemRepository.save(carritoItem);  // Guardamos la actualización

            // Recalcular totales del carrito después de actualizar la cantidad
            Carrito carrito = carritoItem.getCarrito();
            BigDecimal precioTotal = BigDecimal.ZERO;
            int cantidadTotal = 0;

            // Obtener todos los items del carrito
            for (CarritoItem item : carritoItemRepository.findByCarrito_CarritoId(carrito.getCarritoId())) {
                precioTotal = precioTotal.add(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
                cantidadTotal += item.getCantidad();
            }

            // Llamar al método actualizarTotales
            carrito.actualizarTotales(precioTotal, cantidadTotal);
            carritoRepository.save(carrito);
        }
    }

    @Override
    public void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId) {
        // Buscar el carrito item asociado al usuario y el producto
        CarritoItem carritoItem = carritoItemRepository.findByCarrito_Usuario_UsuarioIdAndProducto_ProductoId(usuarioId, productoId);
        if (carritoItem != null) {
            // Eliminar el item del carrito
            carritoItemRepository.delete(carritoItem);

            // Recalcular totales del carrito después de eliminar el item
            Carrito carrito = carritoItem.getCarrito();
            BigDecimal precioTotal = BigDecimal.ZERO;
            int cantidadTotal = 0;

            // Obtener todos los items del carrito
            for (CarritoItem item : carritoItemRepository.findByCarrito_CarritoId(carrito.getCarritoId())) {
                precioTotal = precioTotal.add(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
                cantidadTotal += item.getCantidad();
            }

            // Llamar al método actualizarTotales
            carrito.actualizarTotales(precioTotal, cantidadTotal);
            carritoRepository.save(carrito); // Guardamos después de actualizar totales
        }
    }
}

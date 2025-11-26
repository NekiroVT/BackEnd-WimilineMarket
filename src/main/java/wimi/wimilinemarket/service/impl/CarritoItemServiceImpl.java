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


        Carrito carrito = carritoRepository.findByUsuario_UsuarioIdAndEstadoTrue(usuarioId);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(new Usuario(usuarioId));
            carrito.setEstado(true);
            carritoRepository.save(carrito);
        }


        CarritoItem carritoItem = carritoItemRepository.findByCarrito_Usuario_UsuarioIdAndProducto_ProductoId(usuarioId, productoId);
        if (carritoItem == null) {

            carritoItem = new CarritoItem();
            carritoItem.setCarrito(carrito);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(cantidad);
            carritoItem.setPrecioUnitario(producto.getPrecio());
            carritoItem.setEstado(true);
            carritoItemRepository.save(carritoItem);
        } else {

            if (producto.getStock() < cantidad) {
                carritoItem.setCantidad(producto.getStock());
                carritoItem.setEstado(false);
            } else {
                carritoItem.setCantidad(cantidad);
                carritoItem.setEstado(true);
            }
            carritoItemRepository.save(carritoItem);
        }


        carritoService.recalcularTotalesCarrito(carrito.getCarritoId());


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

    }

    @Override
    public void eliminarProductoDelCarrito(UUID usuarioId, UUID productoId) {

    }
}

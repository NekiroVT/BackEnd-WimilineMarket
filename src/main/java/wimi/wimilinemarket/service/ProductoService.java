package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.ProductoDTO;
import wimi.wimilinemarket.entities.Producto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoService {

    ProductoDTO createProducto(ProductoDTO productoDTO);

    Optional<ProductoDTO> findById(UUID productoId);

    List<ProductoDTO> findAll();

    List<ProductoDTO> findAllByCategoria(UUID categoriaId);

    ProductoDTO updateProducto(UUID productoId, ProductoDTO productoDTO);

    void deleteProducto(UUID productoId);

    List<ProductoDTO> findProductosNuevos();
}

package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.ProductoDTO;
import wimi.wimilinemarket.entities.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoService {

    // Crear un nuevo Producto
    ProductoDTO createProducto(ProductoDTO productoDTO);

    // Obtener un producto por su ID
    Optional<ProductoDTO> findById(UUID productoId);

    // Obtener todos los productos
    List<ProductoDTO> findAll();

    // Obtener productos por categoría
    List<ProductoDTO> findAllByCategoria(UUID categoriaId);

    // Actualizar datos de un producto
    ProductoDTO updateProducto(UUID productoId, ProductoDTO productoDTO);

    // Eliminar un producto
    void deleteProducto(UUID productoId);
}

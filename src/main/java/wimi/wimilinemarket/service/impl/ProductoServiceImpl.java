package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.dto.ProductoDTO;
import wimi.wimilinemarket.entities.Producto;
import wimi.wimilinemarket.repository.ProductoRepository;
import wimi.wimilinemarket.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setProductoId(UUID.randomUUID());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setDetalles(productoDTO.getDetalles());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setActivo(true); // default activo
        producto.setCreatedAt(java.time.LocalDateTime.now());

        Producto savedProducto = productoRepository.save(producto);
        return mapToDTO(savedProducto);
    }

    @Override
    public Optional<ProductoDTO> findById(UUID productoId) {
        return productoRepository.findById(productoId).map(this::mapToDTO);
    }

    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<ProductoDTO> findAllByCategoria(UUID categoriaId) {
        // Cambia la llamada al repositorio para usar el nombre correcto del método
        List<Producto> productos = productoRepository.findAllByCategoria_CategoriaId(categoriaId);

        // Mapea la lista de productos a DTOs
        return productos.stream().map(this::mapToDTO).toList();
    }


    @Override
    public ProductoDTO updateProducto(UUID productoId, ProductoDTO productoDTO) {
        Optional<Producto> optProducto = productoRepository.findById(productoId);
        if (optProducto.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = optProducto.get();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setDetalles(productoDTO.getDetalles());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setActivo(productoDTO.getActivo());

        Producto updatedProducto = productoRepository.save(producto);
        return mapToDTO(updatedProducto);
    }

    @Override
    public void deleteProducto(UUID productoId) {
        productoRepository.deleteById(productoId);
    }

    private ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
                producto.getProductoId(),
                producto.getCategoria().getCategoriaId(), // Aquí se mapea la categoria_id
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getDetalles(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getImagenUrl(),
                producto.getActivo(),
                producto.getCreatedAt()
        );
    }
}

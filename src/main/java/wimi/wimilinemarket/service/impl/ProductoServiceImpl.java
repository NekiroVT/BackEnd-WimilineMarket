package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.dto.ProductoDTO;
import wimi.wimilinemarket.entities.Categoria;
import wimi.wimilinemarket.entities.Producto;
import wimi.wimilinemarket.repository.CategoriaRepository;
import wimi.wimilinemarket.repository.ProductoRepository;
import wimi.wimilinemarket.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {

        if (productoDTO.getCategoriaId() == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setProductoId(UUID.randomUUID());
        producto.setCategoria(categoria);
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setDetalles(productoDTO.getDetalles());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setActivo(true);
        producto.setCreatedAt(LocalDateTime.now());

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
        return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> findAllByCategoria(UUID categoriaId) {
        List<Producto> productos = productoRepository.findAllByCategoria_CategoriaId(categoriaId);
        return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductoDTO updateProducto(UUID productoId, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


        if (productoDTO.getCategoriaId() == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);

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

    @Override
    public List<ProductoDTO> findProductosNuevos() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(24);
        List<Producto> productosNuevos = productoRepository.findProductosNuevosDesde(fechaLimite);
        return productosNuevos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
                producto.getProductoId(),
                producto.getCategoria() != null ? producto.getCategoria().getCategoriaId() : null,
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

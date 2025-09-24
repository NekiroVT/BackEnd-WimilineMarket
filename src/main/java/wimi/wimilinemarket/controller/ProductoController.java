package wimi.wimilinemarket.controller;

import wimi.wimilinemarket.dto.ProductoDTO;
import wimi.wimilinemarket.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por ID
    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable UUID productoId) {
        return productoService.findById(productoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Obtener productos por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(@PathVariable UUID categoriaId) {
        List<ProductoDTO> productos = productoService.findAllByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO createdProducto = productoService.createProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProducto);
    }

    // Actualizar un producto
    @PutMapping("/{productoId}")
    public ResponseEntity<?> updateProducto(@PathVariable UUID productoId, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProducto = productoService.updateProducto(productoId, productoDTO);
        return ResponseEntity.ok(updatedProducto);
    }

    // Eliminar un producto
    @DeleteMapping("/{productoId}")
    public ResponseEntity<?> deleteProducto(@PathVariable UUID productoId) {
        productoService.deleteProducto(productoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Obtener productos nuevos (últimas 24 horas)
    @GetMapping("/nuevos")
    public ResponseEntity<List<ProductoDTO>> getProductosNuevos() {
        List<ProductoDTO> productosNuevos = productoService.findProductosNuevos();
        return ResponseEntity.ok(productosNuevos);
    }
}

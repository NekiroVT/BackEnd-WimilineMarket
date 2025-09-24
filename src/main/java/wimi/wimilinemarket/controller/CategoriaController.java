package wimi.wimilinemarket.controller;

import wimi.wimilinemarket.dto.CategoriaDTO;
import wimi.wimilinemarket.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    // Obtener categoría por ID
    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable UUID categoriaId) {
        return categoriaService.findById(categoriaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<?> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO createdCategoria = categoriaService.createCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria);
    }

    // Actualizar una categoría
    @PutMapping("/{categoriaId}")
    public ResponseEntity<?> updateCategoria(@PathVariable UUID categoriaId, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO updatedCategoria = categoriaService.updateCategoria(categoriaId, categoriaDTO);
        return ResponseEntity.ok(updatedCategoria);
    }

    // Eliminar una categoría
    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<?> deleteCategoria(@PathVariable UUID categoriaId) {
        categoriaService.deleteCategoria(categoriaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

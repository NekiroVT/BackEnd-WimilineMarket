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


    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable UUID categoriaId) {
        return categoriaService.findById(categoriaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    @PostMapping
    public ResponseEntity<?> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO createdCategoria = categoriaService.createCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria);
    }


    @PutMapping("/{categoriaId}")
    public ResponseEntity<?> updateCategoria(@PathVariable UUID categoriaId, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO updatedCategoria = categoriaService.updateCategoria(categoriaId, categoriaDTO);
        return ResponseEntity.ok(updatedCategoria);
    }


    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<?> deleteCategoria(@PathVariable UUID categoriaId) {
        categoriaService.deleteCategoria(categoriaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.CategoriaDTO;
import wimi.wimilinemarket.entities.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaService {

    // Crear una nueva categoría
    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);

    // Obtener una categoría por su ID
    Optional<CategoriaDTO> findById(UUID categoriaId);

    // Obtener todas las categorías
    List<CategoriaDTO> findAll();

    // Actualizar datos de una categoría
    CategoriaDTO updateCategoria(UUID categoriaId, CategoriaDTO categoriaDTO);

    // Eliminar una categoría
    void deleteCategoria(UUID categoriaId);
}

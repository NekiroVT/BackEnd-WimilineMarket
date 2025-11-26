package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.CategoriaDTO;
import wimi.wimilinemarket.entities.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaService {

    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);

    Optional<CategoriaDTO> findById(UUID categoriaId);

    List<CategoriaDTO> findAll();

    CategoriaDTO updateCategoria(UUID categoriaId, CategoriaDTO categoriaDTO);

    void deleteCategoria(UUID categoriaId);
}

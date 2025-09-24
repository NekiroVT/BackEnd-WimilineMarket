package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.dto.CategoriaDTO;
import wimi.wimilinemarket.entities.Categoria;
import wimi.wimilinemarket.repository.CategoriaRepository;
import wimi.wimilinemarket.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaDTO createCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(UUID.randomUUID());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setActiva(true); // default activo
        categoria.setCreatedAt(java.time.LocalDateTime.now());

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return mapToDTO(savedCategoria);
    }

    @Override
    public Optional<CategoriaDTO> findById(UUID categoriaId) {
        return categoriaRepository.findById(categoriaId).map(this::mapToDTO);
    }

    @Override
    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CategoriaDTO updateCategoria(UUID categoriaId, CategoriaDTO categoriaDTO) {
        Optional<Categoria> optCategoria = categoriaRepository.findById(categoriaId);
        if (optCategoria.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada");
        }

        Categoria categoria = optCategoria.get();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setActiva(categoriaDTO.getActiva());

        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return mapToDTO(updatedCategoria);
    }

    @Override
    public void deleteCategoria(UUID categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

    private CategoriaDTO mapToDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getCategoriaId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getActiva(),
                categoria.getCreatedAt()
        );
    }
}

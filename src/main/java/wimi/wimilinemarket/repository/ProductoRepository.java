package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.Producto;

import java.util.List;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    // Obtener todos los productos por ID de categoría
    List<Producto> findAllByCategoria_CategoriaId(UUID categoriaId);
}

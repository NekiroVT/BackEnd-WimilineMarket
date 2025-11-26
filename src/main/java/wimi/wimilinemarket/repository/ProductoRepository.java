package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.Producto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {


    List<Producto> findAllByCategoria_CategoriaId(UUID categoriaId);


    @Query("SELECT p FROM Producto p WHERE p.createdAt >= :fechaLimite")
    List<Producto> findProductosNuevosDesde(LocalDateTime fechaLimite);
}

// CarritoRepository.java
package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.Carrito;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
    // Aquí puedes agregar consultas personalizadas si las necesitas, por ejemplo, buscar por usuario
    //List<Carrito> findByUsuarioId(UUID usuarioId);
}
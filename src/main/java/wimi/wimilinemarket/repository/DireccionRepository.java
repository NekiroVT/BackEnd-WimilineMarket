package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.Direccion;

import java.util.List;
import java.util.UUID;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, UUID> {
    List<Direccion> findAllByUsuario_UsuarioId(UUID usuarioId);
}

package wimi.wimilinemarket.repository;

import wimi.wimilinemarket.entities.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
    // Buscar un carrito activo para un usuario
    Carrito findByUsuario_UsuarioIdAndEstadoTrue(UUID usuarioId);
}

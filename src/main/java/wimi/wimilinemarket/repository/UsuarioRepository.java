package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Buscar un Usuario por su email (login principal)
    Optional<Usuario> findByEmail(String email);

    // Comprobar si existe un Usuario por email
    boolean existsByEmail(String email);
}

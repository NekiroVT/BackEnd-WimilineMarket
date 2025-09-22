package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.UsuarioDTO;
import wimi.wimilinemarket.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    // Crear un nuevo Usuario a partir de un DTO
    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);

    // Buscar un usuario por su email (login principal)
    Optional<Usuario> findByEmail(String email);

    // Comprobar si existe un usuario por email
    boolean existsByEmail(String email);

    // Guardar una entidad Usuario directamente (para register)
    Usuario save(Usuario usuario);

    // (Opcional) Obtener un UsuarioDTO por ID
    Optional<UsuarioDTO> findById(UUID id);

    // (Opcional) Actualizar datos del Usuario
    UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO);
}

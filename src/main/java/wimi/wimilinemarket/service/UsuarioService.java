package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.UsuarioDTO;
import wimi.wimilinemarket.dto.UsuarioProfileDTO;
import wimi.wimilinemarket.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Usuario save(Usuario usuario);

    Optional<UsuarioDTO> findById(UUID id);

    UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO);

    UsuarioProfileDTO getUserProfile(UUID usuarioId);

    // 🔴 NUEVO
    void deleteUsuario(UUID id);
}

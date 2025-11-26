package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.dto.UsuarioDTO;
import wimi.wimilinemarket.dto.UsuarioProfileDTO;
import wimi.wimilinemarket.entities.Usuario;
import wimi.wimilinemarket.repository.UsuarioRepository;
import wimi.wimilinemarket.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        String encodedPassword = passwordEncoder.encode(usuarioDTO.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(UUID.randomUUID());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setActivo(true);
        usuario.setPassword(encodedPassword);

        Usuario saved = usuarioRepository.save(usuario);
        return mapToDTO(saved);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<UsuarioDTO> findById(UUID id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public UsuarioDTO updateUsuario(UUID id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setActivo(usuarioDTO.getActivo());

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }

        Usuario updated = usuarioRepository.save(usuario);
        return mapToDTO(updated);
    }

    // ==================== PERFIL ====================

    @Override
    @Transactional(readOnly = true)
    public UsuarioProfileDTO getUserProfile(UUID usuarioId) {
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String fullName = buildFullName(u.getNombre(), u.getApellido());

        // Foto: si en BD no hay (null/blank), devolvemos null
        String raw = u.getFotoUrl();
        String photoUrl = (raw != null && !raw.trim().isEmpty()) ? raw.trim() : null;

        String email = u.getEmail();

        return UsuarioProfileDTO.builder()
                .fullName(fullName)
                .photoUrl(photoUrl)  // puede ser null
                .email(email)
                .build();
    }

    @Override
    public void deleteUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Gracias a cascade = CascadeType.ALL y orphanRemoval = true en Usuario.direcciones
        // al eliminar el usuario se eliminarán automáticamente todas sus direcciones.
        usuarioRepository.delete(usuario);
    }


    // ==================== HELPERS ====================

    private String buildFullName(String nombre, String apellido) {
        String n = nombre != null ? nombre.trim() : "";
        String a = apellido != null ? apellido.trim() : "";
        String out = (n + " " + a).trim();
        return out.isEmpty() ? null : out;
    }

    // ==================== MAPPER ====================

    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setActivo(usuario.getActivo());
        dto.setCreatedAt(usuario.getCreatedAt());
        return dto;
    }
}

package wimi.wimilinemarket.config;

import wimi.wimilinemarket.entities.Usuario;
import wimi.wimilinemarket.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * En Spring Security el parámetro se llama "username",
     * pero aquí lo usamos como EMAIL (login por email).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        boolean enabled = usuario.getActivo() == null ? true : usuario.getActivo();

        return User.withUsername(usuario.getEmail())      // username = email
                .password(usuario.getPassword())          // hash BCrypt u otro
                .authorities(Collections.emptyList())     // sin roles por ahora
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!enabled)
                .build();
    }
}

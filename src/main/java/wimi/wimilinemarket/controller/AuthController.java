package wimi.wimilinemarket.controller;

import wimi.wimilinemarket.config.JwtTokenProvider;
import wimi.wimilinemarket.dto.LoginRequestDTO;
import wimi.wimilinemarket.dto.RegisterRequestDTO;
import wimi.wimilinemarket.entities.Usuario;
import wimi.wimilinemarket.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // ✅ LOGIN (email + password)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        Optional<Usuario> opt = usuarioService.findByEmail(req.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "❌ Credenciales incorrectas"));
        }

        Usuario usuario = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "❌ Credenciales incorrectas"));
        }

        // 🔑 Generar JWT
        String accessToken = jwtTokenProvider.generateToken(usuario);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", accessToken,
                "user", Map.of(
                        "id", usuario.getUsuarioId(),
                        "nombre", usuario.getNombre(),
                        "apellido", usuario.getApellido(),
                        "email", usuario.getEmail(),
                        "telefono", usuario.getTelefono(),
                        "activo", usuario.getActivo()
                )
        ));
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO req) {
        if (usuarioService.existsByEmail(req.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "❌ El email ya está registrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(UUID.randomUUID());
        usuario.setNombre(req.getNombre());
        usuario.setApellido(req.getApellido());
        usuario.setEmail(req.getEmail());
        usuario.setTelefono(req.getTelefono());
        usuario.setActivo(true);
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));

        usuarioService.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("success", true, "message", "✅ Usuario registrado con éxito")
        );
    }
}

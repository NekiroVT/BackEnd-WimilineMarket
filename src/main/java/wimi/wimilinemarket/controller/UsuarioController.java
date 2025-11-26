package wimi.wimilinemarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wimi.wimilinemarket.dto.UsuarioProfileDTO;
import wimi.wimilinemarket.service.UsuarioService;
import org.springframework.security.core.Authentication;



import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET /api/usuarios/{id}/perfil  -> solo nombre completo y foto
    @GetMapping("/{id}/perfil")
    public ResponseEntity<UsuarioProfileDTO> getPerfil(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(usuarioService.getUserProfile(id));
    }



    // GET /api/usuarios/me/perfil  -> obtiene el usuario desde el token
    @GetMapping("/me/perfil")
    public ResponseEntity<UsuarioProfileDTO> getMiPerfil(Authentication auth) {

        // auth.getName() contiene el "subject" del token (normalmente el ID de usuario)
        UUID id = UUID.fromString(auth.getName());

        return ResponseEntity.ok(usuarioService.getUserProfile(id));
    }

}

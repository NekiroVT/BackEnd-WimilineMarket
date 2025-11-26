package wimi.wimilinemarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wimi.wimilinemarket.dto.DireccionDTO;
import wimi.wimilinemarket.service.DireccionService;
import wimi.wimilinemarket.config.JwtTokenProvider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionService direccionService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public DireccionController(DireccionService direccionService, JwtTokenProvider jwtTokenProvider) {
        this.direccionService = direccionService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Método para crear una nueva dirección
    @PostMapping
    public ResponseEntity<DireccionDTO> crearDireccion(@RequestBody DireccionDTO direccionDTO, HttpServletRequest request) {

        String token = request.getHeader("Authorization");


        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }


        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);

        if (usuarioId == null) {
            return ResponseEntity.status(401).build();
        }


        direccionDTO.setUsuarioId(UUID.fromString(usuarioId));


        DireccionDTO nuevaDireccion = direccionService.crearDireccion(direccionDTO);
        return ResponseEntity.ok(nuevaDireccion);
    }

    // Método para obtener una dirección por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> obtenerDireccionPorId(@PathVariable UUID id, HttpServletRequest request) {

        String token = request.getHeader("Authorization");


        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }


        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);


        Optional<DireccionDTO> direccion = direccionService.obtenerDireccionPorId(id);

        if (direccion.isPresent()) {

            if (direccion.get().getUsuarioId().toString().equals(usuarioId)) {
                return ResponseEntity.ok(direccion.get());
            } else {
                return ResponseEntity.status(403).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Método para obtener direcciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionDTO>> obtenerDireccionesPorUsuario(@PathVariable UUID usuarioId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");


        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }


        String usuarioIdToken = jwtTokenProvider.getUsuarioIdFromToken(token);


        if (!usuarioIdToken.equals(usuarioId.toString())) {
            return ResponseEntity.status(403).build();
        }


        List<DireccionDTO> direcciones = direccionService.obtenerDireccionesPorUsuario(usuarioId);
        return ResponseEntity.ok(direcciones);
    }

    // Método para actualizar una dirección
    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizarDireccion(@PathVariable UUID id, @RequestBody DireccionDTO direccionDTO, HttpServletRequest request) {

        String token = request.getHeader("Authorization");


        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }


        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);


        Optional<DireccionDTO> direccionExistente = direccionService.obtenerDireccionPorId(id);

        if (direccionExistente.isPresent()) {

            if (direccionExistente.get().getUsuarioId().toString().equals(usuarioId)) {
                direccionDTO.setUsuarioId(UUID.fromString(usuarioId));
                DireccionDTO direccionActualizada = direccionService.actualizarDireccion(id, direccionDTO);
                return ResponseEntity.ok(direccionActualizada);
            } else {
                return ResponseEntity.status(403).build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    // Método para eliminar una dirección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable UUID id, HttpServletRequest request) {

        String token = request.getHeader("Authorization");


        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        token = token.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }


        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);


        Optional<DireccionDTO> direccionExistente = direccionService.obtenerDireccionPorId(id);

        if (direccionExistente.isPresent()) {

            if (direccionExistente.get().getUsuarioId().toString().equals(usuarioId)) {
                direccionService.eliminarDireccion(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}

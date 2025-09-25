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
        // Obtener el token del header Authorization
        String token = request.getHeader("Authorization");

        // Verificar si el token está presente y es válido
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        token = token.substring(7); // Eliminar "Bearer " del token

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Token inválido
        }

        // Obtener el usuarioId desde el token
        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);

        if (usuarioId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Asignamos el usuarioId desde el token al DTO
        direccionDTO.setUsuarioId(UUID.fromString(usuarioId));

        // Crear la dirección
        DireccionDTO nuevaDireccion = direccionService.crearDireccion(direccionDTO);
        return ResponseEntity.ok(nuevaDireccion);
    }

    // Método para obtener una dirección por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> obtenerDireccionPorId(@PathVariable UUID id, HttpServletRequest request) {
        // Obtener el token del header Authorization
        String token = request.getHeader("Authorization");

        // Verificar si el token está presente y es válido
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        token = token.substring(7); // Eliminar "Bearer " del token

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Token inválido
        }

        // Obtener el usuarioId desde el token
        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);

        // Buscar la dirección
        Optional<DireccionDTO> direccion = direccionService.obtenerDireccionPorId(id);

        if (direccion.isPresent()) {
            // Verificamos que la dirección pertenezca al usuario autenticado
            if (direccion.get().getUsuarioId().toString().equals(usuarioId)) {
                return ResponseEntity.ok(direccion.get()); // Retornar la dirección
            } else {
                return ResponseEntity.status(403).build(); // Forbidden: La dirección no pertenece al usuario
            }
        }
        return ResponseEntity.notFound().build(); // Dirección no encontrada
    }

    // Método para obtener direcciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionDTO>> obtenerDireccionesPorUsuario(@PathVariable UUID usuarioId, HttpServletRequest request) {
        // Obtener el token del header Authorization
        String token = request.getHeader("Authorization");

        // Verificar si el token está presente y es válido
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        token = token.substring(7); // Eliminar "Bearer " del token

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Token inválido
        }

        // Obtener el usuarioId desde el token
        String usuarioIdToken = jwtTokenProvider.getUsuarioIdFromToken(token);

        // Comprobar que el usuarioID del token sea el mismo que el que se pasa por parámetro
        if (!usuarioIdToken.equals(usuarioId.toString())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Obtener las direcciones del usuario
        List<DireccionDTO> direcciones = direccionService.obtenerDireccionesPorUsuario(usuarioId);
        return ResponseEntity.ok(direcciones);
    }

    // Método para actualizar una dirección
    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizarDireccion(@PathVariable UUID id, @RequestBody DireccionDTO direccionDTO, HttpServletRequest request) {
        // Obtener el token del header Authorization
        String token = request.getHeader("Authorization");

        // Verificar si el token está presente y es válido
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        token = token.substring(7); // Eliminar "Bearer " del token

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Token inválido
        }

        // Obtener el usuarioId desde el token
        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);

        // Buscar la dirección
        Optional<DireccionDTO> direccionExistente = direccionService.obtenerDireccionPorId(id);

        if (direccionExistente.isPresent()) {
            // Verificamos que la dirección pertenezca al usuario autenticado
            if (direccionExistente.get().getUsuarioId().toString().equals(usuarioId)) {
                direccionDTO.setUsuarioId(UUID.fromString(usuarioId)); // Aseguramos que el usuarioId esté correcto
                DireccionDTO direccionActualizada = direccionService.actualizarDireccion(id, direccionDTO);
                return ResponseEntity.ok(direccionActualizada);
            } else {
                return ResponseEntity.status(403).build(); // Forbidden: La dirección no pertenece al usuario
            }
        }

        return ResponseEntity.notFound().build(); // Dirección no encontrada
    }

    // Método para eliminar una dirección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable UUID id, HttpServletRequest request) {
        // Obtener el token del header Authorization
        String token = request.getHeader("Authorization");

        // Verificar si el token está presente y es válido
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        token = token.substring(7); // Eliminar "Bearer " del token

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).build(); // Token inválido
        }

        // Obtener el usuarioId desde el token
        String usuarioId = jwtTokenProvider.getUsuarioIdFromToken(token);

        // Buscar la dirección
        Optional<DireccionDTO> direccionExistente = direccionService.obtenerDireccionPorId(id);

        if (direccionExistente.isPresent()) {
            // Verificamos que la dirección pertenezca al usuario autenticado
            if (direccionExistente.get().getUsuarioId().toString().equals(usuarioId)) {
                direccionService.eliminarDireccion(id);
                return ResponseEntity.noContent().build(); // Dirección eliminada correctamente
            } else {
                return ResponseEntity.status(403).build(); // Forbidden: La dirección no pertenece al usuario
            }
        }

        return ResponseEntity.notFound().build(); // Dirección no encontrada
    }
}

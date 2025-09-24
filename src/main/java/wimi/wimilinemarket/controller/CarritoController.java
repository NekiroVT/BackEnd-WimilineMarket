// CarritoController.java
package wimi.wimilinemarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wimi.wimilinemarket.dto.CarritoDTO;
import wimi.wimilinemarket.entities.Carrito;
import wimi.wimilinemarket.service.CarritoService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    // Crear un carrito nuevo
    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.crearCarrito(carrito);
        return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
    }

    // Obtener un carrito por ID
    @GetMapping("/{carritoId}")
    public ResponseEntity<CarritoDTO> obtenerCarritoPorId(@PathVariable UUID carritoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(carritoId);
        CarritoDTO carritoDTO = new CarritoDTO(
                carrito.getCarritoId(),
                carrito.getEstado(),
                carrito.getUsuario().getUsuarioId().toString()  // Solo mandas el ID del usuario
        );
        return new ResponseEntity<>(carritoDTO, HttpStatus.OK);
    }

    // Obtener todos los carritos
    @GetMapping
    public ResponseEntity<List<CarritoDTO>> obtenerCarritos() {
        List<Carrito> carritos = carritoService.obtenerCarritos();

        // Convertir cada Carrito a CarritoDTO
        List<CarritoDTO> carritosDTO = carritos.stream()
                .map(carrito -> new CarritoDTO(
                        carrito.getCarritoId(),
                        carrito.getEstado(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getUsuarioId().toString() : null  // Evitar null
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(carritosDTO, HttpStatus.OK);
    }

    // Actualizar un carrito
    @PutMapping("/{carritoId}")
    public ResponseEntity<Carrito> actualizarCarrito(
            @PathVariable UUID carritoId, @RequestBody Carrito carrito) {
        Carrito carritoActualizado = carritoService.actualizarCarrito(carritoId, carrito);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    // Eliminar un carrito
    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable UUID carritoId) {
        carritoService.eliminarCarrito(carritoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

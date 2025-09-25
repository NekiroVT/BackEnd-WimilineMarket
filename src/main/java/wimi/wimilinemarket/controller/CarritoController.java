package wimi.wimilinemarket.controller;

import wimi.wimilinemarket.entities.Carrito;
import wimi.wimilinemarket.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable UUID usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }
}

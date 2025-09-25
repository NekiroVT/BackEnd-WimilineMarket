package wimi.wimilinemarket.controller;

import wimi.wimilinemarket.dto.CarritoItemDTO;
import wimi.wimilinemarket.service.CarritoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carrito-item")
public class CarritoItemController {

    private final CarritoItemService carritoItemService;

    public CarritoItemController(CarritoItemService carritoItemService) {
        this.carritoItemService = carritoItemService;
    }

    @PostMapping("/agregar/{usuarioId}/{productoId}")
    public ResponseEntity<CarritoItemDTO> agregarProductoAlCarrito(
            @PathVariable UUID usuarioId,
            @PathVariable UUID productoId,
            @RequestBody int cantidad) {

        CarritoItemDTO carritoItemDTO = carritoItemService.agregarProductoAlCarrito(usuarioId, productoId, cantidad);
        return ResponseEntity.ok(carritoItemDTO);
    }

    @DeleteMapping("/eliminar/{usuarioId}/{productoId}")
    public ResponseEntity<Void> eliminarProductoDelCarrito(
            @PathVariable UUID usuarioId,
            @PathVariable UUID productoId) {

        carritoItemService.eliminarProductoDelCarrito(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }
}

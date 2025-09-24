// CheckoutController.java
package wimi.wimilinemarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wimi.wimilinemarket.service.CheckoutService;

import java.util.UUID;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/transferir/{carritoId}/{pedidoId}")
    public ResponseEntity<Void> transferir(@PathVariable UUID carritoId, @PathVariable UUID pedidoId) {
        checkoutService.transferirCarritoAPedido(carritoId, pedidoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirmar-pago/{carritoId}/{pedidoId}")
    public ResponseEntity<Void> confirmarPago(@PathVariable UUID carritoId, @PathVariable UUID pedidoId) {
        checkoutService.confirmarPago(carritoId, pedidoId);
        return ResponseEntity.ok().build();
    }
}

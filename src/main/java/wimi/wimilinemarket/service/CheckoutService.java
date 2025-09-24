// CheckoutService.java
package wimi.wimilinemarket.service;

import java.util.UUID;

public interface CheckoutService {

    /**
     * Copia todos los CarritoItem del carrito dado al Pedido indicado
     * y elimina los CarritoItem SOLO si todo salió bien.
     */
    void transferirCarritoAPedido(UUID carritoId, UUID pedidoId);

    /**
     * Marca el pedido como PAGADO y vacía el carrito asociado (si aplica).
     * Llama internamente a transferirCarritoAPedido si aún no se transfirió.
     */
    void confirmarPago(UUID carritoId, UUID pedidoId);
}

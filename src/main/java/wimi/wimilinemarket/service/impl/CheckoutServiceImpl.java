// CheckoutServiceImpl.java
package wimi.wimilinemarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wimi.wimilinemarket.entities.CarritoItem;
import wimi.wimilinemarket.entities.Pedido;
import wimi.wimilinemarket.entities.PedidoItem;
import wimi.wimilinemarket.repository.CarritoItemRepository;
import wimi.wimilinemarket.repository.PedidoItemRepository;
import wimi.wimilinemarket.repository.PedidoRepository;
import wimi.wimilinemarket.service.CheckoutService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CarritoItemRepository carritoItemRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;

    // 1) Cuando el pedido está PAGADO, transferir todos los items del carrito a PedidoItem
    @Override
    @Transactional
    public void transferirCarritoAPedido(UUID carritoId, UUID pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));

        // Idempotencia: si ya se transfirieron items, no hacer nada
        if (pedidoItemRepository.existsByPedido_PedidoId(pedidoId)) return;

        // Traer todos los items del carrito asociado
        List<CarritoItem> items = carritoItemRepository.findByCarrito_CarritoId(carritoId);
        if (items.isEmpty()) return; // Si no hay items, salir

        // Transferir cada item a PedidoItem
        for (CarritoItem ci : items) {
            PedidoItem pi = new PedidoItem();
            pi.setPedidoItemId(UUID.randomUUID());
            pi.setPedido(pedido);
            pi.setProducto(ci.getProducto());
            pi.setCantidad(ci.getCantidad());
            pi.setPrecioUnitario(ci.getPrecioUnitario() == null
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(ci.getPrecioUnitario()));
            pi.setCreatedAt(LocalDateTime.now());

            pedidoItemRepository.save(pi);
        }
    }

    // 2) Al confirmar pago: transfiere los items del carrito, cambia estado a "PAGADO" y elimina el carrito
    @Override
    @Transactional
    public void confirmarPago(UUID carritoId, UUID pedidoId) {
        // Verifica si el pedido existe
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));

        // Si no se han transferido los items, transfiera el carrito a pedido
        if (!pedidoItemRepository.existsByPedido_PedidoId(pedidoId)) {
            transferirCarritoAPedido(carritoId, pedidoId);
        }

        // Cambiar estado a PAGADO
        pedido.setEstado("PAGADO");
        pedidoRepository.save(pedido);

        // Eliminar los items del carrito (ahora que ya están en PedidoItem)
        if (carritoItemRepository.countByCarrito_CarritoId(carritoId) > 0) {
            carritoItemRepository.deleteAllByCarritoId(carritoId);
        }
    }
}

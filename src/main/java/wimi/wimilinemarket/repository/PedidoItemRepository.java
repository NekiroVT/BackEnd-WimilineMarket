// PedidoItemRepository.java
package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.PedidoItem;

import java.util.UUID;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, UUID> {
    boolean existsByPedido_PedidoId(UUID pedidoId);
}

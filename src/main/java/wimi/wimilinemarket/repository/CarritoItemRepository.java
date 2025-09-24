// CarritoItemRepository.java
package wimi.wimilinemarket.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import wimi.wimilinemarket.entities.CarritoItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, UUID> {

    List<CarritoItem> findByCarrito_CarritoId(UUID carritoId);

    @Modifying
    @Query("DELETE FROM CarritoItem ci WHERE ci.carrito.carritoId = :carritoId")
    void deleteAllByCarritoId(UUID carritoId);

    long countByCarrito_CarritoId(UUID carritoId);

}

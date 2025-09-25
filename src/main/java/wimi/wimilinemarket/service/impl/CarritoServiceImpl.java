package wimi.wimilinemarket.service.impl;

import wimi.wimilinemarket.entities.Carrito;
import wimi.wimilinemarket.entities.CarritoItem;
import wimi.wimilinemarket.repository.CarritoRepository;
import wimi.wimilinemarket.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;

    @Autowired
    public CarritoServiceImpl(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    @Override
    public Carrito obtenerCarritoPorUsuario(UUID usuarioId) {
        return carritoRepository.findByUsuario_UsuarioIdAndEstadoTrue(usuarioId);
    }

    @Override
    public void recalcularTotalesCarrito(UUID carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        BigDecimal precioTotal = BigDecimal.ZERO;
        int cantidadTotal = 0;

        for (CarritoItem item : carrito.getCarritoItems()) {
            if (item.getEstado()) { // Solo contar los items activos
                precioTotal = precioTotal.add(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
                cantidadTotal += item.getCantidad();
            }
        }

        carrito.actualizarTotales(precioTotal, cantidadTotal);
        carritoRepository.save(carrito);
    }
}

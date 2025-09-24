// CarritoServiceImpl.java
package wimi.wimilinemarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wimi.wimilinemarket.entities.Carrito;
import wimi.wimilinemarket.repository.CarritoRepository;
import wimi.wimilinemarket.service.CarritoService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;

    @Override
    @Transactional
    public Carrito crearCarrito(Carrito carrito) {
        // Asignamos el carritoId si no está presente
        if (carrito.getCarritoId() == null) {
            carrito.setCarritoId(UUID.randomUUID());
        }
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito obtenerCarritoPorId(UUID carritoId) {
        return carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + carritoId));
    }

    @Override
    public List<Carrito> obtenerCarritos() {
        return carritoRepository.findAll();
    }

    @Override
    @Transactional
    public Carrito actualizarCarrito(UUID carritoId, Carrito carrito) {
        // Primero obtenemos el carrito actual
        Carrito carritoExistente = obtenerCarritoPorId(carritoId);

        // Actualizamos los campos del carrito existente con los nuevos valores
        carritoExistente.setEstado(carrito.getEstado());
        carritoExistente.setUsuario(carrito.getUsuario());

        return carritoRepository.save(carritoExistente);
    }

    @Override
    @Transactional
    public void eliminarCarrito(UUID carritoId) {
        carritoRepository.deleteById(carritoId);
    }
}

// CarritoService.java
package wimi.wimilinemarket.service;

import wimi.wimilinemarket.entities.Carrito;

import java.util.List;
import java.util.UUID;

public interface CarritoService {

    Carrito crearCarrito(Carrito carrito); // Crear un nuevo carrito

    Carrito obtenerCarritoPorId(UUID carritoId); // Obtener un carrito por ID

    List<Carrito> obtenerCarritos(); // Obtener todos los carritos

    Carrito actualizarCarrito(UUID carritoId, Carrito carrito); // Actualizar un carrito

    void eliminarCarrito(UUID carritoId); // Eliminar un carrito
}

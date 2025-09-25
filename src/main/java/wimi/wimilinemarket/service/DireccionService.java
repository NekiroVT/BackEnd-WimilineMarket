package wimi.wimilinemarket.service;

import wimi.wimilinemarket.dto.DireccionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DireccionService {

    DireccionDTO crearDireccion(DireccionDTO direccionDTO);

    Optional<DireccionDTO> obtenerDireccionPorId(UUID direccionId);

    List<DireccionDTO> obtenerDireccionesPorUsuario(UUID usuarioId);

    DireccionDTO actualizarDireccion(UUID direccionId, DireccionDTO direccionDTO);

    void eliminarDireccion(UUID direccionId);
}

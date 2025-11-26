package wimi.wimilinemarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wimi.wimilinemarket.dto.DireccionDTO;
import wimi.wimilinemarket.entities.Direccion;
import wimi.wimilinemarket.entities.Usuario;
import wimi.wimilinemarket.repository.DireccionRepository;
import wimi.wimilinemarket.service.DireccionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;

    @Autowired
    public DireccionServiceImpl(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Override
    public DireccionDTO crearDireccion(DireccionDTO direccionDTO) {
        Direccion direccion = new Direccion();
        direccion.setDireccionId(UUID.randomUUID());
        direccion.setUsuario(new Usuario(direccionDTO.getUsuarioId()));
        direccion.setDireccionTexto(direccionDTO.getDireccionTexto());
        direccion.setReferencia(direccionDTO.getReferencia());
        direccion.setDestinatario(direccionDTO.getDestinatario());
        direccion.setDistrito(direccionDTO.getDistrito());
        direccion.setCiudad(direccionDTO.getCiudad());
        direccion.setCodigo_postal(direccionDTO.getCodigo_postal());

        Direccion direccionGuardada = direccionRepository.save(direccion);
        return mapToDTO(direccionGuardada);
    }

    @Override
    public Optional<DireccionDTO> obtenerDireccionPorId(UUID direccionId) {
        Optional<Direccion> direccion = direccionRepository.findById(direccionId);
        return direccion.map(this::mapToDTO);
    }

    @Override
    public List<DireccionDTO> obtenerDireccionesPorUsuario(UUID usuarioId) {
        List<Direccion> direcciones = direccionRepository.findAll();
        return direcciones.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DireccionDTO actualizarDireccion(UUID direccionId, DireccionDTO direccionDTO) {
        if (direccionRepository.existsById(direccionId)) {
            Direccion direccion = new Direccion();
            direccion.setDireccionId(direccionId);
            direccion.setUsuario(new Usuario(direccionDTO.getUsuarioId()));
            direccion.setDireccionTexto(direccionDTO.getDireccionTexto());
            direccion.setReferencia(direccionDTO.getReferencia());
            direccion.setDestinatario(direccionDTO.getDestinatario());
            direccion.setDistrito(direccionDTO.getDistrito());
            direccion.setCiudad(direccionDTO.getCiudad());
            direccion.setCodigo_postal(direccionDTO.getCodigo_postal());

            Direccion direccionActualizada = direccionRepository.save(direccion);
            return mapToDTO(direccionActualizada);
        } else {
            throw new RuntimeException("Dirección no encontrada");
        }
    }

    @Override
    public void eliminarDireccion(UUID direccionId) {
        direccionRepository.deleteById(direccionId);
    }

    private DireccionDTO mapToDTO(Direccion direccion) {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setDireccionId(direccion.getDireccionId());
        direccionDTO.setUsuarioId(direccion.getUsuario().getUsuarioId());
        direccionDTO.setDireccionTexto(direccion.getDireccionTexto());
        direccionDTO.setReferencia(direccion.getReferencia());
        direccionDTO.setDestinatario(direccion.getDestinatario());
        direccionDTO.setDistrito(direccion.getDistrito());
        direccionDTO.setCiudad(direccion.getCiudad());
        direccionDTO.setCodigo_postal(direccion.getCodigo_postal());
        return direccionDTO;
    }
}

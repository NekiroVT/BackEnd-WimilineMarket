package wimi.wimilinemarket.dto;
import lombok.Data;

import java.util.UUID;
    // CarritoDTO.java


    @Data
    public class CarritoDTO {
        private UUID carritoId;
        private String estado;
        private String usuarioId;  // Solo enviar el ID del usuario, no todo el objeto Usuario

        public CarritoDTO(UUID carritoId, String estado, String usuarioId) {
            this.carritoId = carritoId;
            this.estado = estado;
            this.usuarioId = usuarioId;
        }
    }



package wimi.wimilinemarket.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class DireccionDTO {

    private UUID direccionId;
    private UUID usuarioId;
    private String direccionTexto;
    private String referencia;
    private String destinatario;
    private String distrito;
    private String ciudad;
    private String codigo_postal;
}

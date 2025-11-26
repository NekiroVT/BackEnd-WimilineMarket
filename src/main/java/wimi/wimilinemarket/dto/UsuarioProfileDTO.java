package wimi.wimilinemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Solo nombre completo y foto de perfil (URL). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProfileDTO {
    private String fullName;
    private String photoUrl; // puede ser null o una URL por defecto
    private String email;
}

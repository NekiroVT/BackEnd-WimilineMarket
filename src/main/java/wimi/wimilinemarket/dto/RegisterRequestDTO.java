package wimi.wimilinemarket.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;
}

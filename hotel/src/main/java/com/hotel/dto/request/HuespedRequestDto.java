package com.hotel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuespedRequestDto {

    @NotBlank(message = "El nombre no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre  debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre  debe tener entre 3 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "El numero de documento  no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre  debe tener entre 3 y 30 caracteres")
    private String documentoIdentidad;

    @NotBlank(message = "El correo  no puede ser vacío")
    @Email(message = "Debe ingresar el correo con el formato correctamente")
    private String email;

    @NotNull(message = "debe ingresar los datos del perfil")
    private PerfilContactoRequestDto perfilContacto;

}

package org.equipos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipoRequest {

  @NotBlank(message = "El Nombre del equipo es obligatorio")
  private String nombre;

  private String liga;
  private String pais;

}

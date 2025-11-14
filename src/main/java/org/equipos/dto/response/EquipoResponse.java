package org.equipos.dto.response;

import lombok.Data;

@Data
public class EquipoResponse {
  private Long id;
  private String nombre;
  private String liga;
  private String pais;

}

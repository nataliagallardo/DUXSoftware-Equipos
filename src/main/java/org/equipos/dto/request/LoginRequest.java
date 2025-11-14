package org.equipos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "El campo username es obligatorio.")
  private String username;

  @NotBlank(message = "El campo password es obligatorio.")
  private String password;}

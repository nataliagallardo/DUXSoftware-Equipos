package org.equipos.service;

import org.equipos.dto.response.EquipoResponse;
import org.equipos.dto.request.EquipoRequest;

import java.util.List;

public interface EquipoService {

  List<EquipoResponse> getAll();
  EquipoResponse getById(Long id);
  List<EquipoResponse> searchByNombre(String nombre);
  EquipoResponse create(EquipoRequest request);
  EquipoResponse update(Long id, EquipoRequest request);

  void delete(Long id);
}

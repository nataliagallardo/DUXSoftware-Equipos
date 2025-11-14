package org.equipos.service.impl;

import org.equipos.dto.response.EquipoResponse;
import org.equipos.dto.request.EquipoRequest;
import org.equipos.exception.BadRequestException;
import org.equipos.exception.NotFoundException;
import org.equipos.model.Equipo;
import org.equipos.repository.EquipoRepository;
import org.equipos.service.EquipoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

  private static final String EQUIPO_NO_ENCONTRADO = "Equipo no encontrado";
  private static final String SOLICITUD_INVALIDA = "La solicitud es invalida";
  private static final String NO_SE_ENCONTRO_NINGUN_EQUIPO = "No se encontro ningun equipo.";

  private final EquipoRepository equipoRepository;

  public EquipoServiceImpl(EquipoRepository equipoRepository) {
    this.equipoRepository = equipoRepository;
  }

  private EquipoResponse toDto(Equipo equipo) {
    EquipoResponse dto = new EquipoResponse();
    dto.setId(equipo.getId());
    dto.setNombre(equipo.getNombre());
    dto.setLiga(equipo.getLiga());
    dto.setPais(equipo.getPais());
    return dto;
  }

  private Equipo toEntity(EquipoRequest request) {
    return Equipo.builder()
        .nombre(request.getNombre())
        .liga(request.getLiga())
        .pais(request.getPais())
        .build();
  }

  @Override
  public List<EquipoResponse> getAll() {
    return equipoRepository.findAll().stream().map(this::toDto).toList();
  }

  @Override
  public EquipoResponse getById(Long id) {
    Equipo e = equipoRepository.findById(id).orElseThrow(() -> new NotFoundException(EQUIPO_NO_ENCONTRADO));
    return toDto(e);
  }

  @Override
  public List<EquipoResponse> searchByNombre(String nombre) {
    List<Equipo> equipos = equipoRepository.findByNombreContainingIgnoreCase(nombre);
    if (equipos.isEmpty()) throw new NotFoundException(NO_SE_ENCONTRO_NINGUN_EQUIPO);
    return equipos.stream().map(this::toDto).toList();
  }

  @Override
  public EquipoResponse create(EquipoRequest request) {
    if (request.getNombre() == null || request.getNombre().isBlank())
      throw new BadRequestException(SOLICITUD_INVALIDA);
    Equipo equipo = equipoRepository.save(toEntity(request));
    return toDto(equipo);
  }

  @Override
  public EquipoResponse update(Long id, EquipoRequest request) {
    Equipo equipo = equipoRepository.findById(id).orElseThrow(() -> new NotFoundException(EQUIPO_NO_ENCONTRADO));
    equipo.setNombre(request.getNombre());
    equipo.setLiga(request.getLiga());
    equipo.setPais(request.getPais());
    return toDto(equipoRepository.save(equipo));
  }

  @Override
  public void delete(Long id) {
    if (!equipoRepository.existsById(id)) throw new NotFoundException(EQUIPO_NO_ENCONTRADO);
    equipoRepository.deleteById(id);
  }
}

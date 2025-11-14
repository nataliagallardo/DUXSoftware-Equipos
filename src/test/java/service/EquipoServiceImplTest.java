package service;

import org.equipos.dto.request.EquipoRequest;
import org.equipos.dto.response.EquipoResponse;
import org.equipos.exception.BadRequestException;
import org.equipos.exception.NotFoundException;
import org.equipos.model.Equipo;
import org.equipos.repository.EquipoRepository;
import org.equipos.service.impl.EquipoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EquipoServiceImplTest {

  @Mock
  private EquipoRepository equipoRepository;

  @InjectMocks
  private EquipoServiceImpl equipoService;

  private Equipo equipo;
  private EquipoRequest request;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    equipo = Equipo.builder()
        .id(1L)
        .nombre("Real Madrid")
        .liga("La Liga")
        .pais("España")
        .build();

    request = EquipoRequest.builder()
        .nombre("Real Madrid")
        .liga("La Liga")
        .pais("España")
        .build();
  }

  @Test
  void getAll() {
    when(equipoRepository.findAll()).thenReturn(List.of(equipo));

    List<EquipoResponse> result = equipoService.getAll();

    assertEquals(1, result.size());
    assertEquals("Real Madrid", result.get(0).getNombre());
  }

  @Test
  void getByIdOk() {
    when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

    EquipoResponse result = equipoService.getById(1L);

    assertNotNull(result);
    assertEquals("Real Madrid", result.getNombre());
    verify(equipoRepository).findById(1L);
  }

  @Test
  void getByIdWhenNotExists() {
    when(equipoRepository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> equipoService.getById(99L));
  }

  @Test
  void createOK() {
    when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo);

    EquipoResponse result = equipoService.create(request);

    assertNotNull(result);
    assertEquals("Real Madrid", result.getNombre());
    verify(equipoRepository).save(any(Equipo.class));
  }

  @Test
  void createWhenNombreIsBlank() {
    request.setNombre(" ");
    assertThrows(BadRequestException.class, () -> equipoService.create(request));
  }

  @Test
  void update_OK() {
    when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));
    when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo);

    EquipoRequest equipoRequest = EquipoRequest.builder()
        .nombre("Barcelona")
        .liga("La Liga")
        .pais("España")
        .build();

    EquipoResponse response = equipoService.update(1L, equipoRequest);
    assertEquals("Barcelona", response.getNombre());
  }

  @Test
  void updateWhenEquipoNotExists() {
    when(equipoRepository.findById(2L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> equipoService.update(2L, request));
  }

  @Test
  void deleteOk() {
    when(equipoRepository.existsById(1L)).thenReturn(true);

    equipoService.delete(1L);

    verify(equipoRepository).deleteById(1L);
  }

  @Test
  void deleteWhenNotExists() {
    when(equipoRepository.existsById(9L)).thenReturn(false);
    assertThrows(NotFoundException.class, () -> equipoService.delete(9L));
  }

}

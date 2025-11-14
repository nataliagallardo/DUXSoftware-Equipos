package org.equipos.controller;

import jakarta.validation.Valid;
import org.equipos.dto.response.EquipoResponse;
import org.equipos.dto.request.EquipoRequest;
import org.equipos.service.EquipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

  private final EquipoService service;

  public EquipoController(EquipoService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<EquipoResponse>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EquipoResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @GetMapping("/buscar")
  public ResponseEntity<List<EquipoResponse>> search(@RequestParam String nombre) {
    return ResponseEntity.ok(service.searchByNombre(nombre));
  }

  @PostMapping
  public ResponseEntity<EquipoResponse> create(@Valid @RequestBody EquipoRequest request) {
    return ResponseEntity.status(201).body(service.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<EquipoResponse> update(@PathVariable Long id, @RequestBody EquipoRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}

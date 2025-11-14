package org.equipos.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.equipos.config.JwtUtil;
import org.equipos.dto.request.LoginRequest;
import org.equipos.dto.response.LoginResponse;
import org.equipos.exception.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtil jwtUtil;
  private static final String TEST = "test";
  private static final String PASS = "12345";
  private static final String AUTENTICACION_FALLIDA = "Autenticacion fallida";

  public AuthController(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    if (TEST.equals(request.getUsername()) && PASS.equals(request.getPassword())) {
      String token = jwtUtil.generateToken(request.getUsername());
      return ResponseEntity.ok(new LoginResponse(token));
    }
    throw new AuthException(AUTENTICACION_FALLIDA);
  }}

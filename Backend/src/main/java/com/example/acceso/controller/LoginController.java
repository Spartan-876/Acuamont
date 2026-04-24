package com.example.acceso.controller;

import com.example.acceso.model.Usuario;
import com.example.acceso.service.UsuarioService;
import com.example.acceso.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController // Cambiado para devolver JSON
@RequestMapping("/api/auth") // Ruta base para autenticación
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil; // Inyectaremos nuestra utilidad de JWT

    public LoginController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("usuario");
        String password = credentials.get("clave");

        Optional<Usuario> usuarioOpt = usuarioService.findByUsuario(username);

        // 1. Validar existencia
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
        }

        Usuario user = usuarioOpt.get();

        // 2. Validar estado
        if (user.getEstado() != 1) {
            return ResponseEntity.status(403).body(Map.of("error", "Usuario inactivo"));
        }

        // 3. Validar contraseña
        if (usuarioService.verificarContrasena(password, user.getClave())) {
            // GENERAR TOKEN JWT
            String token = jwtUtil.generarToken(user.getUsuario());

            // Respuesta estructurada para Angular
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", user.getUsuario());
            response.put("nombre", user.getNombre());
            response.put("perfil", user.getPerfil().getNombre());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
        }
    }
}
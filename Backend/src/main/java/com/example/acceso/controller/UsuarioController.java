package com.example.acceso.controller;

import com.example.acceso.model.Usuario;
import com.example.acceso.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarUsuariosApi() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            response.put("success", true);
            response.put("data", usuarios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al listar usuarios");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            response.put("success", false);
            response.put("errors", errores);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
            response.put("success", true);
            response.put("usuario", usuarioGuardado);
            response.put("message", usuario.getId() != null ? "Actualizado" : "Creado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuario -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("data", usuario);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (usuarioService.obtenerUsuarioPorId(id).isPresent()) {
            usuarioService.eliminarUsuario(id);
            response.put("success", true);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
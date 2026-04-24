package com.example.acceso.controller;

import com.example.acceso.model.Perfil;
import com.example.acceso.model.Usuario;
import com.example.acceso.service.PerfilesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/perfiles")
public class PerfilControlller {

    private final PerfilesService perfilesService;

    public PerfilControlller(PerfilesService perfilesService) {
        this.perfilesService = perfilesService;
    }

    @GetMapping("/listar")
    public String listarPerfiles(Model model) {
        List<Perfil> perfiles = perfilesService.listarPerfiles();
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("formUsuario", new Perfil());
        return "perfiles";
    }

    @GetMapping("/api/listar")
    @ResponseBody
    public ResponseEntity<?> listarPerfilesApi() {
        Map<String, Object> response = new HashMap<>();
        List<Perfil> perfiles = perfilesService.listarPerfiles();
        response.put("success", true);
        response.put("data", perfiles);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarPerfilAjax(@Valid @RequestBody Perfil perfil, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            response.put("success", false);
            response.put("message", "Datos inválidos");
            response.put("errors", errores);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Perfil perfilGuardado = perfilesService.guardarPerfil(perfil);
            response.put("success", true);
            response.put("perfil", perfilGuardado);
            response.put("message",
                    perfil.getId() != null ? "Perfil actualizado correctamente" : "Perfil creado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al guardar el perfil: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerPerfil(@PathVariable Long id) {
        try {
            return perfilesService.obtenerPerfilPorId(id).map(perfil -> {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", perfil);
                return ResponseEntity.ok(response);
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener el perfil: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/api/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarPerfilAjax(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!perfilesService.obtenerPerfilPorId(id).isPresent()) {
                response.put("success", false);
                response.put("message", "Perfil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            perfilesService.eliminarPerfil(id);
            response.put("success", true);
            response.put("message", "Perfil eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el perfil: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/api/cambiar-estado/{id}")
    @ResponseBody
    public ResponseEntity<?> cambiarEstadoPerfilAjax(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            return perfilesService.cambiarEstadoPerfil(id).map(perfil -> {
                response.put("success", true);
                response.put("perfil", perfil);
                response.put("message", "Estado del perfil actualizado correctamente");
                return ResponseEntity.ok(response);
            }).orElseGet(() -> {
                response.put("success", false);
                response.put("message", "Perfil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            });
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cambiar el estado del perfil: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}

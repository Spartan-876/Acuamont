package com.example.acceso.service;

import com.example.acceso.model.Perfil;
import com.example.acceso.repository.PerfilRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilesService {

    private final PerfilRepository perfilRepository;

    public PerfilesService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Transactional(readOnly = true)
    public List<Perfil> listarPerfiles() {
        return perfilRepository.findAllByEstadoNot(2);
    }

    @Transactional
    public Perfil guardarPerfil(Perfil perfil) {
        try {
            if (perfil.getNombre() == null || perfil.getNombre().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            perfil.setNombre(perfil.getNombre().trim());
            return perfilRepository.save(perfil);
        }catch (DataIntegrityViolationException e) {
            String message = e.getMessage().toLowerCase();
            if (message.contains("nombre")) {
                throw new IllegalArgumentException("El perfil ya existe");
            }else {
                throw new IllegalArgumentException("Error de integridad de datos");
            }
        }catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar el perfil: " + e.getMessage());

        }
    }

    @Transactional(readOnly = true)
    public Optional<Perfil> obtenerPerfilPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return perfilRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Perfil> findByPerfil(String nombre) {
        return perfilRepository.findByNombre(nombre);
    }

    @Transactional
    public void eliminarPerfil(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del perfil es obligatorio");
        }

        Perfil perfil = obtenerPerfilPorId(id).
                orElseThrow(()-> new IllegalArgumentException("Perfil no encontrado"));

        perfil.setEstado(2);
        perfilRepository.save(perfil);

    }

    @Transactional
    public Optional<Perfil> cambiarEstadoPerfil(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        return perfilRepository.findById(id).map(perfil -> {
            // Solo alterna entre 0 (inactivo) y 1 (activo)
            if (perfil.getEstado() == 1) {
                perfil.setEstado(0); // Desactivar
            } else if (perfil.getEstado() == 0) {
                perfil.setEstado(1); // Activar
            }
            // No se hace nada si el estado es 2 (eliminado)
            return perfilRepository.save(perfil);
        });
    }

    @Transactional(readOnly = true)
    public boolean existePerfil(String nombrePerfil) {
        if (nombrePerfil == null || nombrePerfil.trim().isEmpty()) {
            return false;
        }
        // Utiliza el método eficiente del repositorio
        return perfilRepository.existsByNombre(nombrePerfil.trim().toLowerCase());
    }

}

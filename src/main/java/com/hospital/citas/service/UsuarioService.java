package com.hospital.citas.service;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean validarCredenciales(Usuario usuarioIniciaSesion) {
        Usuario usuarioEncontrado = usuarioRepository.findById(usuarioIniciaSesion.getId()).orElse(null);

        if(usuarioEncontrado != null) {
            String passwordEncontrada = usuarioEncontrado.getContrasennaHash();
            String passwordEncriptada = encriptarPassword(usuarioIniciaSesion.getContrasennaHash());

            if(passwordEncontrada.equals(passwordEncriptada)) {
                return true;
            }
        }
        return false;
    }

    public String consultarPaginaInicioPorRol(Usuario usuario){
        Usuario usuarioEncontrado = usuarioRepository.findById(usuario.getId()).orElse(null);
        if(usuarioEncontrado != null) {
            return usuarioEncontrado.getRol().getNombrePaginaInicio();
        }
        return "homeNoEncontrada";
    }

    private String encriptarPassword(String passwordPorEncriptar) {
        // Desarrollar el proceso de encriptación de la contraseña
        return passwordPorEncriptar;
    }
}
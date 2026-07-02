package com.hospital.citas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearCuenta(Usuario usuarioNuevo) {
        Usuario usuarioRegistrado = usuarioRepository.save(usuarioNuevo);
        if(usuarioRegistrado != null) {
            Long idUsuarioRealizoAccion = 1L;
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(1L, usuarioRegistrado.getId(), "El usuario ha sido registrado en el sistema.", idUsuarioRealizoAccion);
        }
        return usuarioRegistrado;
    }

    public Usuario buscarPorCorreoElectronico(String correoElectronicoBuscar) {
        return usuarioRepository.findByCorreoElectronico(correoElectronicoBuscar).orElse(null);
    }

    public Usuario buscarPorIdentificacion(String identificacionBuscar) {
        return usuarioRepository.findByIdentificacion(identificacionBuscar).orElse(null);
    }
}
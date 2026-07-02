package com.hospital.citas.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.CodigoResetContrasennaRepository;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoResetContrasennaRepository codigoResetContrasennaRepository;

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

    public void procesarRecuperacionContrasenna(UsuarioInicioSesionDTO usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByCorreoElectronico(usuario.getCorreo()).orElse(null);
        if(usuarioEncontrado != null) {
            //Procesar la solicitud una vez confirmado que el correo existe
            List<CodigoResetContrasenna> listaCodigosReset = codigoResetContrasennaRepository.findAllByUsuario(usuarioEncontrado);
            if(listaCodigosReset != null){
                if(listaCodigosReset.size() > 0) {
                    guardarCodigosOTP_Expirados(listaCodigosReset);
                }
            }

            // Elimina códigos OTP VIEJOS
            codigoResetContrasennaRepository.deleteByUsuario(usuarioEncontrado);

            // Genera el nuevo código
            String codigoOTP = generarCodigoOTP();

            // GUARDA EL CÓDIGO EN ACTIVOS Y GENERADOS
            CodigoResetContrasenna codigoReset = new CodigoResetContrasenna();
            codigoReset.setUsuario(usuarioEncontrado);
            codigoReset.setCodigoGenerado(codigoOTP);
            // sp_ConsultaFechaHoraActualServer CONSULTAR LA HORA ACTUAL EN EL SERVIDOR DE BASE DE DATOS Y A ESA SUMARLE LOS 5 MINUTOS
            codigoReset.setFechaExpiracion(LocalDateTime.now().plusMinutes(5));
            codigoResetContrasennaRepository.save(codigoReset);
        }
    }

    private String generarCodigoOTP() {
        SecureRandom random = new SecureRandom();
        int numero = 100000 + random.nextInt(900000); // Rango de 100000 a 999999
        return String.valueOf(numero);
    }

    private void guardarCodigosOTP_Expirados(List<CodigoResetContrasenna> listaCodigosEncontrados) {
        for (CodigoResetContrasenna codigoResetContrasenna : listaCodigosEncontrados) {
            if(codigoResetContrasenna.estaExpirado()) {
                codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Expirado(codigoResetContrasenna.getCodigoGenerado(), codigoResetContrasenna.getUsuario().getId(), codigoResetContrasenna.getFechaExpiracion());
            }
        }
    }
}
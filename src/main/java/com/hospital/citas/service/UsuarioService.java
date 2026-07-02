package com.hospital.citas.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.CodigoResetContrasennaDTO;
import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.CodigoResetContrasennaRepository;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final CodigoResetContrasennaService codigoResetContrasennaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoResetContrasennaRepository codigoResetContrasennaRepository;

    @Autowired
    private ConsultaDBServerService consultaDBServerService;

    @Autowired
    private CorreoService correoService;

    UsuarioService(CorreoService correoService, CodigoResetContrasennaService codigoResetContrasennaService) {
        this.correoService = correoService;
        this.codigoResetContrasennaService = codigoResetContrasennaService;
    }

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
            LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
            List<CodigoResetContrasenna> listaCodigosReset = codigoResetContrasennaRepository.findAllByUsuario(usuarioEncontrado);
            if(listaCodigosReset != null){
                if(listaCodigosReset.size() > 0) {
                    guardarCodigosOTP_Expirados(listaCodigosReset, fechaHoraActual);
                }
            }

            // Elimina códigos OTP VIEJOS
            codigoResetContrasennaRepository.eliminarCodigosAntiguosPorIDUsuario(usuarioEncontrado.getId());

            // Genera el nuevo código
            String codigoOTP = generarCodigoOTP();

            // GUARDA EL CÓDIGO EN ACTIVOS Y GENERADOS
            CodigoResetContrasenna codigoReset = new CodigoResetContrasenna();
            codigoReset.setUsuario(usuarioEncontrado);
            codigoReset.setCodigoGenerado(codigoOTP);
            codigoReset.setFechaExpiracion(fechaHoraActual.plusMinutes(5));
            codigoResetContrasennaRepository.save(codigoReset);

            // Procede a enviar el código generado por correo.
            correoService.enviarCorreoCodigo(usuarioEncontrado.getCorreoElectronico(), codigoOTP);
        }
    }

    private String generarCodigoOTP() {
        SecureRandom random = new SecureRandom();
        int numero = 100000 + random.nextInt(900000); // Rango de 100000 a 999999
        return String.valueOf(numero);
    }

    private void guardarCodigosOTP_Expirados(List<CodigoResetContrasenna> listaCodigosEncontrados, LocalDateTime fechaHoraRevision) {
        for (CodigoResetContrasenna codigoResetContrasenna : listaCodigosEncontrados) {
            if(fechaHoraRevision.isAfter(codigoResetContrasenna.getFechaExpiracion())) {
                codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Expirado(codigoResetContrasenna.getCodigoGenerado(), codigoResetContrasenna.getUsuario().getId(), codigoResetContrasenna.getFechaExpiracion());
            }
        }
    }

    public boolean codigoSeguridadEsValido(CodigoResetContrasennaDTO codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoSeguridad(), usuario).orElse(null);
        return codigoResetEncontrado != null;
    }

    public boolean codigoSeguridadEstaActivo(CodigoResetContrasennaDTO codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoSeguridad(), usuario).orElse(null);

        if(codigoResetEncontrado != null) {
            LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
            return fechaHoraActual.isBefore(codigoResetEncontrado.getFechaExpiracion());
        }
        return false;
    }

    public void procesarCodigoSeguridad(CodigoResetContrasennaDTO codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Usado(codigoSeguridad.getCodigoSeguridad(), usuario.getId());
        codigoResetContrasennaRepository.eliminarCodigoSeguridad_Usado(codigoSeguridad.getCodigoSeguridad(), usuario.getId());
    }
}
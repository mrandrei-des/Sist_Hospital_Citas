package com.hospital.citas.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.model.dto.UsuarioSessionDTO;
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

    @Autowired
    private ConsultaDBServerService consultaDBServerService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario crearCuenta(Usuario usuarioNuevo) {

        usuarioNuevo.setContrasennaHash(passwordEncoder.encode(usuarioNuevo.getContrasennaHash()));
        Usuario usuarioRegistrado = usuarioRepository.save(usuarioNuevo);
        if(usuarioRegistrado != null) {
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(1L, usuarioRegistrado.getId(), "El usuario ha sido registrado en el sistema.", usuarioRegistrado.getId());
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
            codigoReset.setFechaExpiracion(fechaHoraActual.plusMinutes(15));
            codigoResetContrasennaRepository.save(codigoReset);
            codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Generado(codigoOTP, usuarioEncontrado.getId());
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

    public boolean codigoSeguridadEsValido(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoGenerado(), usuario).orElse(null);
        return codigoResetEncontrado != null;
    }

    public boolean codigoSeguridadEstaActivo(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoGenerado(), usuario).orElse(null);

        if(codigoResetEncontrado != null) {
            LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
            return fechaHoraActual.isBefore(codigoResetEncontrado.getFechaExpiracion());
        }
        return false;
    }

    public void procesarCodigoSeguridad(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Usado(codigoSeguridad.getCodigoGenerado(), usuario.getId());
        codigoResetContrasennaRepository.eliminarCodigoSeguridad_Usado(codigoSeguridad.getCodigoGenerado(), usuario.getId());
    }

    public boolean procesarCambioContrasenna(UsuarioInicioSesionDTO usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByCorreoElectronico(usuario.getCorreo()).orElse(null);
        if(usuarioEncontrado != null) {
            usuarioRepository.cambioContrasenna(usuarioEncontrado.getId(), passwordEncoder.encode(usuario.getContrasenna()));
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(4L, usuarioEncontrado.getId(), "El usuario realizó la recuperación de contraseña.", usuarioEncontrado.getId());
            return true;
        }
        return false;
    }

    public void reenviarCodigoResetContrasenna(String correoUsuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        if(usuarioEncontrado != null) {
            CodigoResetContrasenna codigoResetContrasenna = codigoResetContrasennaRepository.findByUsuario(usuarioEncontrado).orElse(null);
            if(codigoResetContrasenna != null) {
                String codigoOTP = codigoResetContrasenna.getCodigoGenerado();
                correoService.enviarCorreoCodigo(usuarioEncontrado.getCorreoElectronico(), codigoOTP);
            }
        }
    }

    public UsuarioSessionDTO construirUsuarioSessionDTO(String correoUsuario) {
        Usuario usuario =  usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        UsuarioSessionDTO usuarioSessionDTO = new UsuarioSessionDTO();

        if (usuario != null) {
            usuarioSessionDTO.setId(usuario.getId());
            usuarioSessionDTO.setNombre(usuario.getNombre());
            usuarioSessionDTO.setPrimerApellido(usuario.getPrimerApellido());
            usuarioSessionDTO.setSegundoApellido(usuario.getSegundoApellido());
        }
        return usuarioSessionDTO;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
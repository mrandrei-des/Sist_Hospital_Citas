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
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.CodigoResetContrasennaRepository;
import com.hospital.citas.repository.UsuarioRepository;

// Servicio dedicado en la creación y mantenimiento de usuarios así como la validación y verificación de su existencia en el sistema.
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

    // Método que registra un nuevo usuario paciente al sistema. 
    // Utilizado en el registro automático de pacientes.
    public Usuario crearCuenta(Usuario usuarioNuevo) {
        usuarioNuevo.setContrasennaHash(passwordEncoder.encode(usuarioNuevo.getContrasennaHash()));
        Usuario usuarioRegistrado = usuarioRepository.save(usuarioNuevo);
        if(usuarioRegistrado != null) {
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(1L, usuarioRegistrado.getId(), "El usuario ha sido registrado en el sistema.", usuarioRegistrado.getId());
        }
        return usuarioRegistrado;
    }

    // Método que utiliza el admin para registrar un nuevo usuario.
    // Usado en el registro de usuarios para el administrador.
    public boolean crearCuentaPorAdmin(Usuario usuarioNuevo, Long idUsuarioAdmin) {
        usuarioNuevo.setContrasennaHash(passwordEncoder.encode(usuarioNuevo.getContrasennaHash()));
        Usuario usuarioRegistrado = usuarioRepository.save(usuarioNuevo);
        if(usuarioRegistrado != null) {
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(1L, usuarioRegistrado.getId(), "El usuario ha sido registrado en el sistema por un usuario admin.", idUsuarioAdmin);
            return true;
        }
        return false;
    }

    // Método que busca y devuelve el usuario que encuentre por medio del correo indicado.
    // Usado en las validaciones para verificar si el correo ya está registrado en el sistema.
    public Usuario buscarPorCorreoElectronico(String correoElectronicoBuscar) {
        return usuarioRepository.findByCorreoElectronico(correoElectronicoBuscar).orElse(null);
    }

    // Método que busca y devuelve el usuario que encuentre por medio de la identificación indicada.
    // Usado en las validaciones para verificar si la identificación ya está registrada en el sistema.
    public Usuario buscarPorIdentificacion(String identificacionBuscar) {
        return usuarioRepository.findByIdentificacion(identificacionBuscar).orElse(null);
    }

    // Método que controla y genera el uso del código OTP utilizado en la recuperación de la constraseña.
    // Usado al indicar que el usuario olvidó la contraseña.
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

    // Método que genera y devuelve un código aleatorio.
    // Generado para que el usuario recupere su propia contraseña. 
    private String generarCodigoOTP() {
        SecureRandom random = new SecureRandom();
        int numero = 100000 + random.nextInt(900000); // Rango de 100000 a 999999
        return String.valueOf(numero);
    }

    // Método que respalda en una tabla los código OTP que no se usaron y expiraron.
    private void guardarCodigosOTP_Expirados(List<CodigoResetContrasenna> listaCodigosEncontrados, LocalDateTime fechaHoraRevision) {
        for (CodigoResetContrasenna codigoResetContrasenna : listaCodigosEncontrados) {
            if(fechaHoraRevision.isAfter(codigoResetContrasenna.getFechaExpiracion())) {
                codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Expirado(codigoResetContrasenna.getCodigoGenerado(), codigoResetContrasenna.getUsuario().getId(), codigoResetContrasenna.getFechaExpiracion());
            }
        }
    }

    // Método que valida que el código OTP indicado sea válido para el usuario indicado.
    // Usado en la recuperación de constraseña.
    public boolean codigoSeguridadEsValido(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoGenerado(), usuario).orElse(null);
        return codigoResetEncontrado != null;
    }

    // Método que valida que el código OTP indicado sea esté activo para el usuario indicado.
    // Usado en la recuperación de constraseña.
    public boolean codigoSeguridadEstaActivo(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        CodigoResetContrasenna codigoResetEncontrado = codigoResetContrasennaRepository.findByCodigoGeneradoAndUsuario(codigoSeguridad.getCodigoGenerado(), usuario).orElse(null);

        if(codigoResetEncontrado != null) {
            LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
            return fechaHoraActual.isBefore(codigoResetEncontrado.getFechaExpiracion());
        }
        return false;
    }

    // Método que procesa el código OTP cuando este ya fue utilizado por el usuario. Lo guarda en una tabla de bitácora.
    // Usado en la recuperación de constraseña.
    public void procesarCodigoSeguridad(CodigoResetContrasenna codigoSeguridad, String correoUsuario){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoUsuario).orElse(null);
        codigoResetContrasennaRepository.insertaRegistroBitacoraCodigoOTP_Usado(codigoSeguridad.getCodigoGenerado(), usuario.getId());
        codigoResetContrasennaRepository.eliminarCodigoSeguridad_Usado(codigoSeguridad.getCodigoGenerado(), usuario.getId());
    }

    // Método que procesa y actualiza la nueva contraseña del usuario.
    // Usado en la recuperación de constraseña.
    public boolean procesarCambioContrasenna(UsuarioInicioSesionDTO usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByCorreoElectronico(usuario.getCorreo()).orElse(null);
        if(usuarioEncontrado != null) {
            usuarioRepository.cambioContrasenna(usuarioEncontrado.getId(), passwordEncoder.encode(usuario.getContrasenna()));
            usuarioRepository.insertaRegistroBitacoraCambiosUsuario(4L, usuarioEncontrado.getId(), "El usuario realizó la recuperación de contraseña.", usuarioEncontrado.getId());
            return true;
        }
        return false;
    }

    // Método que reenvia el código OTP del usuario que intenta recuperar su propia contraseña.
    // Usado en la recuperación de constraseña.
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

    // Método que construye y devuelve un objeto con datos básicos del usuario para enviarlo a la variable de sesión de HTTP.
    // Utilizado al iniciar sesión y enviar datos del usuario a las variables de sesión.
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

    // Busca y devuelve al usuario que coincida con el id indicado.
    // Utilizado al cargar el perfil del usuario.
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Método que busca y devuelve al usuario indicado que tenga el estado también indicado.
    public Usuario buscarPorIdYEstado(Long id, Long idEstado) {
        Estado estado = new Estado();
        estado.setId(idEstado);
        return usuarioRepository.findByIdAndEstado(id, estado).orElse(null);
    }

    // Método que busca y verifica si existe un usuario que ya tenga ese correo, pero que el id no sea el mismo. Es decir, que el correo ya exista en el sistema.
    // Usado en las validaciones de correo único.
    public boolean existePorCorreoYNoId(Long id, String correo) {
        return usuarioRepository.existsByCorreoElectronicoAndIdNot(correo, id);
    }

    // Método que busca y verifica si existe un usuario que ya tenga esa identificación, pero que el id no sea el mismo. Es decir, que la identificación ya exista en el sistema.
    // Usado en las validaciones de identificación única.
    public boolean existePorCorreoYNoIdentificacion(Long id, String identificacion) {
        return usuarioRepository.existsByIdentificacionAndIdNot(identificacion, id);
    }
}
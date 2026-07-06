package com.hospital.citas.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.dto.UsuarioMiPerfilDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.MiPerfilRepository;

@Service
public class MiPerfilService {
    private final UsuarioService usuarioService;
    private final MiPerfilRepository miPerfilRepository;
    private final PasswordEncoder passwordEncoder;

    MiPerfilService(MiPerfilRepository miPerfilRepository, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.miPerfilRepository = miPerfilRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean actualizarPerfil(UsuarioMiPerfilDTO usuario, Long idUsuarioLoggeado) {
        Usuario usuarioEncontrado = miPerfilRepository.findById(usuario.getId()).orElse(null);
        if(usuarioEncontrado != null) {
            
            TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
            tipoIdentificacion.setId(usuario.getIdTipoIdentificacion());
            usuarioEncontrado.setTipoIdentificacion(tipoIdentificacion);

            usuarioEncontrado.setIdentificacion(usuario.getIdentificacion());
            usuarioEncontrado.setNombre(usuario.getNombre());
            usuarioEncontrado.setPrimerApellido(usuario.getPrimerApellido());
            usuarioEncontrado.setSegundoApellido(usuario.getSegundoApellido());
            usuarioEncontrado.setCorreoElectronico(usuario.getCorreoElectronico());

            Usuario usuarioActualizado;
            String descripcionBitacora;

            if(idUsuarioLoggeado.compareTo(usuario.getId()) == 0) {
                descripcionBitacora = "El usuario ha actualizado su perfil.";
                if(!usuario.getContrasenna().isEmpty()) {
                    usuarioEncontrado.setContrasennaHash(passwordEncoder.encode(usuario.getContrasenna()));
                }
            }else {
                descripcionBitacora = "El administrador ha actualizado el perfil del usuario.";
                Estado estado = new Estado();
                estado.setId(usuario.getIdEstado());
                usuarioEncontrado.setEstado(estado);

                Rol rol = new Rol();
                rol.setId(usuario.getIdRol());
                usuarioEncontrado.setRol(rol);
            }

            usuarioActualizado = miPerfilRepository.save(usuarioEncontrado);
            if(usuarioActualizado != null) {
                miPerfilRepository.insertaRegistroBitacoraCambios(2L, usuarioActualizado.getId(), descripcionBitacora, idUsuarioLoggeado);
                return true;
            }
        }
        return false;
    }

    public UsuarioMiPerfilDTO buscarPorId(Long idUsuario) {

        UsuarioMiPerfilDTO usuarioDTO = new UsuarioMiPerfilDTO();
        Usuario usuarioEncontrado = usuarioService.buscarPorId(idUsuario);

        if(usuarioEncontrado != null) {
            usuarioDTO.setId(usuarioEncontrado.getId());
            usuarioDTO.setIdTipoIdentificacion(usuarioEncontrado.getTipoIdentificacion().getId());
            usuarioDTO.setIdentificacion(usuarioEncontrado.getIdentificacion());
            usuarioDTO.setContrasenna(usuarioEncontrado.getContrasennaHash());
            usuarioDTO.setNombre(usuarioEncontrado.getNombre());
            usuarioDTO.setPrimerApellido(usuarioEncontrado.getPrimerApellido());
            usuarioDTO.setSegundoApellido(usuarioEncontrado.getSegundoApellido());
            usuarioDTO.setCorreoElectronico(usuarioEncontrado.getCorreoElectronico());
            usuarioDTO.setIdEstado(usuarioEncontrado.getEstado().getId());
            usuarioDTO.setIdRol(usuarioEncontrado.getRol().getId());
        }

        return usuarioDTO;
    }
}

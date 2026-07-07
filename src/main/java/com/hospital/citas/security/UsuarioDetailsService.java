package com.hospital.citas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private HttpSession session;

    UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Estado estado = new Estado();
        estado.setId(4L);
        Usuario usuario = usuarioRepository.findByCorreoElectronicoAndEstado(email, estado)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: "+ email));

        session.setAttribute("idUsuarioLoggeado", usuario.getId());
        session.setAttribute("nombreUsuarioLoggeado", usuario.getNombre());
        session.setAttribute("primerApellidoUsuarioLoggeado", usuario.getPrimerApellido());
        session.setAttribute("segundoApellidoUsuarioLoggeado", usuario.getSegundoApellido());
        session.setAttribute("idRolUsuarioLoggeado", usuario.getRol().getId());
        session.setAttribute("mostrarNotificacion", false);
        session.setAttribute("mensajeNotificacion", "");

        return User.builder()
            .username(usuario.getCorreoElectronico())
            .password(usuario.getContrasennaHash())
            .authorities(new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().getId()))
            .build();
    }
}

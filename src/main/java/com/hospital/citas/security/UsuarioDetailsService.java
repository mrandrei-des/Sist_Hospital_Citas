package com.hospital.citas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: "+ email));

        return User.builder()
            .username(usuario.getCorreoElectronico())
            .password(usuario.getContrasennaHash())
            .authorities(new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().getId()))
            .build();
    }
}

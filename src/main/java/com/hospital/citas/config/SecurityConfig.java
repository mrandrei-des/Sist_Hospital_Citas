package com.hospital.citas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.hospital.citas.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                //rutas publicas 
                // RECURSOS
                .requestMatchers("/css/**", "/js/**", "/img/**", "/fonts/**").permitAll()

                // GET MAPPING PÚBLICO
                .requestMatchers("/login", "/", "/registrarPaciente", "/recuperarContrasenna", "/verificacionCodigo").permitAll()

                // POST MAPPING PÚBLICO
                .requestMatchers("/cuentaNueva", "/procesarRecuperacion", "/procesarVerificacion", "/procesarCambioContrasenna", "/reenviarCodigoReset").permitAll()

                // GET MAPPING PRIVADO PACIENTE Y ADMIN
                .requestMatchers("/acceso-denegado", "/logout", "/mi-perfil/{id}", "/actualizar-mi-cuenta").hasAnyRole("1", "2")

                // GET MAPPING PRIVADO PACIENTE
                .requestMatchers("/inicio").hasAnyRole("1")
                
                // POST MAPPING PRIVADO PACIENTE
                // .requestMatchers().hasAnyRole("1")
                
                // GET MAPPING PRIVADO ADMIN
                .requestMatchers("/registroUsuario", "/mostrarPanel", "/especialidades", "/buscar-especialidad/{id}", "/medicos", "/buscar-medico/{id}", "/deshabilitar-medico/{id}", "/mostrar-panel-pacientes", "/configuracion-horario").hasAnyRole("2")

                // POST MAPPING PRIVADO ADMIN
                .requestMatchers("/registro-especialidad", "/registro-medico", "/procesar-horario-medico", "/api/medicos/consulta/por-especialidad/{id}").hasAnyRole("2")

                .requestMatchers("/inicio").hasAnyRole("1")
                //Todo lo demas, exige que sea ADMIN 
                .anyRequest().hasRole("2") 
            )
            .formLogin(form -> form 
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/acceso-denegado"))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
            // .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
            return http.build();
    }
}
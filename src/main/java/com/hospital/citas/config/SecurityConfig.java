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
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/fonts/**", "/registrarPaciente", "/recuperarContrasenna", "/procesarRecuperacion", "/procesarVerificacion", "/procesarCambioContrasenna", "/reenviarCodigoReset", "/verificacionCodigo", "/cuentaNueva", "/registrarPaciente/cuentaNueva").permitAll()
                //rutas que ADMIN y USUARIO pueden ver
                .requestMatchers("/acceso-denegado").hasAnyRole("1", "2")
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
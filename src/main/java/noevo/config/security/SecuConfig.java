package noevo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecuConfig {

    // Configuracion de seguConfig
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Redireccion el rol "ADMIN" a la ruta /administrador
                        .requestMatchers("/administrador").hasRole("ADMIN")
                        // Auntenticacion
                        .anyRequest().authenticated())
                // Formulario basiquito en el que nos logueamos
                .formLogin(form -> form
                        // Redirreciona a esa controlador despues de loguearse
                        .defaultSuccessUrl("/administrador", true))
                // Http basica para las peticiones
                .httpBasic(httpBasic -> {
                });

        return http.build();
    }

    // Usuario en memoria para probar, despues para tabla en DB
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin_noevo")
                .password(passwordEncoder.encode("noevo123456"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    // Cifado
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
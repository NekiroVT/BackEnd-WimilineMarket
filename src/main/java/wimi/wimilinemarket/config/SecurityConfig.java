package wimi.wimilinemarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Stateless: nada de sesiones de servidor
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Ajusta estas rutas públicas a tus endpoints reales
                        .requestMatchers("/api/usuarios/login", "/api/usuarios/register").permitAll()
                        // Tus endpoints protegidos
                        .requestMatchers("/api/usuarios/me/**").authenticated()
                        // El resto si quieres público: .anyRequest().permitAll()
                        // O si todo lo demás debe estar protegido: .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        // 401 cuando no autenticado (token faltante/expirado/ inválido)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        // 403 cuando autenticado pero sin permiso
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // Coloca tu filtro JWT antes que UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

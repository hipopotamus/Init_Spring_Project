package initproject.global.security.config;

import initproject.global.security.handler.AccountAccessDeniedHandler;
import initproject.global.security.handler.AccountAuthenticationEntryPoint;
import initproject.global.security.filter.JwtAuthenticationFilter;
import initproject.global.security.filter.JwtAuthorizationFilter;
import initproject.global.security.jwt.JwtProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProcessor jwtProcessor;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/account").permitAll()
                .anyRequest().permitAll();

        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProcessor))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProcessor));

        http
                .exceptionHandling()
                .accessDeniedHandler(new AccountAccessDeniedHandler())
                .authenticationEntryPoint(new AccountAuthenticationEntryPoint());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityConfig() {
        return (web -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

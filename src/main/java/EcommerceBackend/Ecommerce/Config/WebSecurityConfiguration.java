package EcommerceBackend.Ecommerce.Config;

import EcommerceBackend.Ecommerce.Filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for web security settings.
 * This class sets up security filters and authentication management.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object used to configure web based security for specific http requests.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs when configuring the SecurityFilterChain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate", "/signup", "/order/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a PasswordEncoder bean that uses BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean.
     *
     * @param config the AuthenticationConfiguration object used to build the AuthenticationManager.
     * @return an AuthenticationManager instance.
     * @throws Exception if an error occurs when creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

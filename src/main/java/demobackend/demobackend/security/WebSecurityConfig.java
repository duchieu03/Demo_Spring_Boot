package demobackend.demobackend.security;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/product/**", "/auth/login"
                        ,"/auth/register","/auth/verify"
                        ,"/auth/reset","auth/change").permitAll()
                //.anyRequest().authenticated()
                .requestMatchers("/auth/user","/auth/me").hasAuthority("USER")
                .requestMatchers("/auth/admin").hasAuthority("ADMIN")
                .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                .anyRequest().permitAll()

        );
        return http.build();
    }


}

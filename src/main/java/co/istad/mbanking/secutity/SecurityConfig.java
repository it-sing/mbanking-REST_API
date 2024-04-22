package co.istad.mbanking.secutity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    @Bean
    DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
               .authorizeHttpRequests(request -> request
                       //  for user
                       .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                       .requestMatchers(HttpMethod.PUT,"/api/v1/users/").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.DELETE,"/api/v1/users/").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.GET,"/api/v1/users/").hasAnyRole("ADMIN" , "STAFF")
                       .requestMatchers(HttpMethod.GET,"/api/v1/account").hasAnyRole("ADMIN")
                       .requestMatchers(HttpMethod.POST,"/api/v1/account/**").hasAnyRole("ADMIN")

                       .anyRequest()
                       .authenticated()
               );

        httpSecurity.httpBasic(Customizer.withDefaults());

        // Disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // Change to STATELESS
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }

}

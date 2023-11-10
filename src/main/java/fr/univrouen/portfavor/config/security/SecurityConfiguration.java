package fr.univrouen.portfavor.config.security;

import fr.univrouen.portfavor.controller.AuthenticationController;
import fr.univrouen.portfavor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

/**
 * Configure the Spring security component.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enable PreAuthorize annotation and more
public class SecurityConfiguration {

    /**
     * The Spring boot environment component.
     */
    @Autowired
    private Environment environment;

    /**
     * The configured password encoder.
     */
    @Autowired
    private SecurityPasswordEncoder passwordEncoder;

    /**
     * The authentication service.
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Method called to configure the Spring security component inside the configuration chain.
     *
     * @param http The HTTP security configuration.
     * @return The customized HTTP configuration as the rest of the configuration chain.
     */
    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        // Set the session management as stateless
        http.sessionManagement(sessionManagementCustomizer ->
            sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Disable CORS and CSRF
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        // Enables the iFrames in dev mode (H2 console)
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")) {
            http.headers(headersCustomizer ->
                headersCustomizer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );
        }

        // Indicates that any endpoint needs an authentication except some of them
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
            authorizeHttpRequestsCustomizer
                // Authorize any requests on the login and register endpoint
                .requestMatchers(new AntPathRequestMatcher(AuthenticationController.LOGIN_ENDPOINT)).permitAll()
                .requestMatchers(new AntPathRequestMatcher(AuthenticationController.REGISTER_ENDPOINT)).permitAll()
                // Authorize any requests on the swagger
                .requestMatchers(
                    new AntPathRequestMatcher("/swagger-ui/**"),
                    new AntPathRequestMatcher("/swagger-ui.html"),
                    new AntPathRequestMatcher("/v3/api-docs/**")
                ).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll();

            // Authorize any requests on the H2 database if the environment is dev
            if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")) {
                authorizeHttpRequestsCustomizer.requestMatchers(PathRequest.toH2Console()).permitAll();
            }

            // Require the authentication on any other endpoints
            authorizeHttpRequestsCustomizer.anyRequest().authenticated();
        });

        // Add the TokenAuthenticationFilter to the security filter list
        http.addFilter(new TokenAuthenticationFilter(this.authenticationManager()));

        return http.build();
    }

    /**
     * @return The authentication manager set up with the AuthenticationService and the good password encoder.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.authenticationService);
        authProvider.setPasswordEncoder(this.passwordEncoder.getPasswordEncoder());
        return new ProviderManager(authProvider);
    }

}

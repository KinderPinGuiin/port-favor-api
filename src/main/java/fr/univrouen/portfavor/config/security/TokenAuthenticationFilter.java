package fr.univrouen.portfavor.config.security;

import fr.univrouen.portfavor.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * Custom Spring security filter that check the given HTTP bearer token.
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private AuthenticationService authenticationService;

    public TokenAuthenticationFilter(
        AuthenticationManager authenticationManager,
        AuthenticationService authenticationService
    ) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    /**
     * This method will get the Authorization HTTP header's value to check if the given Bearer token is valid.
     *
     * @param request  The HTTP request sent to the server.
     * @param response The HTTP response to send to the user.
     * @param chain    The Spring Boot request filter chain (middleware).
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        // Get the Authorization header
        var header = request.getHeader("Authorization");

        // If no Authorization header is found, or if the token doesn't start by Bearer, then we let the rest of the
        // chain handle the request
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            if (response.getStatus() == HttpStatus.FORBIDDEN.value()) {
                logger.info("Someone tried to reach " + request.getRequestURI() + " without providing bearer token.");
            }

            return;
        }

        // Get the token value and get the user associated to it
        var authToken = header.replace("Bearer", "").trim();
        var user = this.authenticationService.getUserByToken(authToken);
        if (user == null) {
            // If the user is null (invalid token) then we don't do anything
            chain.doFilter(request, response);

            return;
        }

        // Authenticate the user to Spring Boot
        var authentication = new UsernamePasswordAuthenticationToken(user, authToken, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}

package com.ouharri.pharma.security;

import com.ouharri.pharma.exceptions.NoAuthenticateUser;
import com.ouharri.pharma.repositories.TokenRepository;
import com.ouharri.pharma.services.spec.JwtService;
import com.ouharri.pharma.services.spec.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.ALL;

/**
 * Service class for handling user logout and token revocation.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    /**
     * Handles user logout by revoking the JWT token.
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication Authentication object representing the current user's authentication details
     */
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ") || !authentication.isAuthenticated())
            throw new NoAuthenticateUser("Token not found");

        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElseThrow(() -> new NoAuthenticateUser("Token not found"));

        if (!(storedToken.isExpired() && storedToken.isRevoked() && jwtService.isTokenExpired(storedToken.getToken()))) {

            userService.disconnect(storedToken.getUser());
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);

            /*
             * The Clear-Site-Data HTTP header is one that browsers support as an instruction to clear cookies,
             * storage, and cache that belong to the owning website.
             * This is a handy and secure way to ensure that everything,
             * including the session cookie, is cleaned up on logout.
             * HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ALL));
             * clearSiteData.logout(request, response, authentication);
             */
            HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ALL));
            clearSiteData.logout(request, response, authentication);

            SecurityContextHolder.clearContext();
        } else
            throw new NoAuthenticateUser("No user is authenticated");
    }
}
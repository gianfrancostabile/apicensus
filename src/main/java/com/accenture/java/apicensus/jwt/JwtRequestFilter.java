package com.accenture.java.apicensus.jwt;

import com.accenture.java.apicensus.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Gian F. S.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Value("${jwt.header.authorization.key}")
    private String jwtHeaderAuthorizationKey;

    @Value("${jwt.token.bearer.prefix}")
    private String jwtTokenBearerPrefix;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        String requestTokenHeader = request.getHeader(jwtHeaderAuthorizationKey);
        String token = (requestTokenHeader != null && requestTokenHeader.startsWith(jwtTokenBearerPrefix + " ")) ?
            requestTokenHeader.substring(7) :
            null;

        setAuthenticationIntoContext(getUsername(token), token, request);
        chain.doFilter(request, response);
    }

    /**
     * Returns the username from the token.
     *
     * @param token the requested JWT token
     * @return the username
     * @see Objects
     * @see JwtToken
     * @see #logger
     * @see IllegalArgumentException
     * @see ExpiredJwtException
     */
    private String getUsername(String token) {
        String username = null;
        try {
            if (Objects.nonNull(token)) {
                username = jwtToken.getUsernameFromToken(token);
            } else {
                logger.debug("JWT Token does not begin with Bearer String");
            }
        } catch (IllegalArgumentException e) {
            logger.debug("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            logger.debug("JWT Token has expired");
        }
        return username;
    }

    /**
     * Set the authentication token into the security
     * context.
     * <br><br>
     * The authentication token will be add if
     * the username is not {@code null} and if the authentication
     * of the security context is {@code null} and
     * if the the token and the user credentials are
     * valid.
     *
     * @param username the username
     * @param token    the token
     * @param request  the request
     * @see HttpServletRequest
     * @see Objects
     * @see SecurityContextHolder
     * @see UserDetails
     * @see JwtUserDetailsService
     * @see JwtToken
     * @see UsernamePasswordAuthenticationToken
     */
    private void setAuthenticationIntoContext(String username, String token, HttpServletRequest request) {
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext()
            .getAuthentication())) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtToken.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
            }
        }
    }
}

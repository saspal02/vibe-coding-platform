package com.saswat.lovable.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final UserContext userContext;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("Incoming request: {}", request.getRequestURI());

    try {
        final String requestHeaderToken = request.getHeader("Authorization");
        if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestHeaderToken.substring("Bearer ".length());

        Claims claims = authUtil.verifyAccessToken(jwtToken);

        if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null);

            SecurityContextHolder.getContext().setAuthentication(auth);

            userContext.setUserId(authUtil.extractUserId(claims));

        }
        filterChain.doFilter(request, response);

    } catch (Exception e) {
        handlerExceptionResolver.resolveException(request, response, null, e);
    }


    }
}

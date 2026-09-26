package com.saswat.lovable.security;

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
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("Incoming request: {}", request.getRequestURI());
            final String requestHeaderToken = request.getHeader("Authorization");
            if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwtToken = requestHeaderToken.substring("Bearer ".length());

            final JwtUserPrincipal userPrincipal = authUtil.verifyAccessToken(jwtToken);

            if (userPrincipal != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.authorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);

        } catch (final Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}

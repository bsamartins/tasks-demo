package org.bsamartins.tasksdemo.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtAuthenticationManager jwtAuthenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header != null) {
            try {
                Authentication authentication = getAuthentication(req);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Throwable t) {
                LOGGER.error("Error validating user");
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(req, res);
    }

    private Authentication getAuthentication(HttpServletRequest request) throws ServletException {
        String token = request.getHeader(HEADER_STRING);
        if(token == null) {
            return null;
        }
        try {
            JwtTokenAuthentication jwtTokenAuthentication = jwtService.parseFromHeader(token);
            return jwtAuthenticationManager.authenticate(jwtTokenAuthentication);
        } catch(JwtException e) {
            throw new ServletException(e);
        }
    }
}
package org.bsamartins.tasksdemo.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class JwtTokenAuthentication extends UsernamePasswordAuthenticationToken {
    public JwtTokenAuthentication(Object principal) {
        super(principal, null, Collections.emptyList());
    }
}

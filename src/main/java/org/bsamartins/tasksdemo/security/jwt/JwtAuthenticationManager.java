package org.bsamartins.tasksdemo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class JwtAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "Expected parameter authentication");
        Assert.isInstanceOf(JwtTokenAuthentication.class, authentication, "Invalid authentication token");

        String username = (String) authentication.getPrincipal();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null) {
            throw new UsernameNotFoundException("User [" + username + "] not found");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
    }
}

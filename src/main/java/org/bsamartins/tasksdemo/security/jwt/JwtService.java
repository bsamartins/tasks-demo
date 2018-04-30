package org.bsamartins.tasksdemo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bsamartins.tasksdemo.security.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class JwtService {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String ISSUER = "org.bsamartins";

    @Value("{application.security.jwt.secret}")
    private String jwtSecret;

    public Token compose(Authentication authentication) throws JwtException {
        String username = (String) authentication.getPrincipal();
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            String jwtToken = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(username)
                    .sign(algorithm);
            return new Token(TOKEN_TYPE + " " + jwtToken);
        } catch(Exception e) {
            throw new JwtException(e);
        }
    }

    public JwtTokenAuthentication parseFromHeader(String token) throws JwtException {
        try {
            token = token.replace(TOKEN_TYPE + " ", "");
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return new JwtTokenAuthentication(jwt.getSubject());
        } catch (UnsupportedEncodingException uee) {
            throw new JwtException(uee);
        }
    }
}

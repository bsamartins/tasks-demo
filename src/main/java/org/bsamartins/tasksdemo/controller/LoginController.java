package org.bsamartins.tasksdemo.controller;

import org.bsamartins.tasksdemo.controller.model.Login;
import org.bsamartins.tasksdemo.security.UsernamePasswordAuthenticationManager;
import org.bsamartins.tasksdemo.security.jwt.JwtService;
import org.bsamartins.tasksdemo.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    private UsernamePasswordAuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping()
    public ResponseEntity<Token> login(@RequestBody Login login) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        Token token = jwtService.compose(authentication);
        return ResponseEntity.ok(token);
    }
}

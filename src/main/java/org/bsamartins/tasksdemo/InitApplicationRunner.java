package org.bsamartins.tasksdemo;

import org.bsamartins.tasksdemo.model.persistence.User;
import org.bsamartins.tasksdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("username");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
    }
}

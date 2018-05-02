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
        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user2);

    }
}

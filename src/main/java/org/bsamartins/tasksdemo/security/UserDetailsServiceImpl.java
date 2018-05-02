package org.bsamartins.tasksdemo.security;

import org.bsamartins.tasksdemo.model.persistence.User;
import org.bsamartins.tasksdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(this::mapUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    private UserDetails mapUserDetails(User user) {
        return new AuthenticatedUser(user);
    }
}

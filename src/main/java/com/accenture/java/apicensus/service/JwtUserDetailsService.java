package com.accenture.java.apicensus.service;

import com.accenture.java.apicensus.entity.UserCredentials;
import com.accenture.java.apicensus.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Gian F. S.
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (Objects.nonNull(username)) {
            UserCredentials userCredentials = this.userCredentialsRepository.findByUsername(username);
            if (Objects.nonNull(userCredentials)) {
                return new User(userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}

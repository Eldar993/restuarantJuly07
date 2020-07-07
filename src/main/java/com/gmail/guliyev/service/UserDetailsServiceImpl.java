package com.gmail.guliyev.service;

import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final UserDetails userDetails = userRepository.findByName(login)
                .map(this::convert)
                .orElseThrow(() -> new UsernameNotFoundException(login + " not found"));

        return userDetails;
    }

    private UserDetails convert(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .roles(user.getUserRole().name())
                .disabled(false)
                .build();
    }
}

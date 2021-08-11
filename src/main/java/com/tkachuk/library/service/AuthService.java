package com.tkachuk.library.service;

import com.tkachuk.library.model.User;
import com.tkachuk.library.repository.UserRepository;
import com.tkachuk.library.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private Authentication auth(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public String generateJwt(User user) {
        Authentication authentication = auth(user);
        return jwtProvider.generateJwtToken(authentication);
    }

    public UserDetails createUserDetails(User user) {
        Authentication authentication = auth(user);
        return (UserDetails) authentication.getPrincipal();
    }

    public void saveUserData(User user) {
        User userNew = new User(user.getUsername(), encoder.encode(user.getPassword()), "ROLE_USER", true);
        userRepository.save(userNew);
    }

    public boolean existsByUsername(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }
}

package com.task.user.servicies;

import com.task.user.dto.JwtResponseDTO;
import com.task.user.entities.User;
import com.task.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthenticationService {

    private JwtUtil jwtUtil;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public JwtResponseDTO authentication(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(login);
        final String token = jwtUtil.generateTokenForUser(userDetails);

        User user = userService.findByLogin(login).get();

        return new JwtResponseDTO(user.getId(), user.getLogin(), user.getFirstName(), token, user.getRoles());
    }
}

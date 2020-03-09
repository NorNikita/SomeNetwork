package com.task.user.controller;

import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.servicies.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<?> getAuthenticated(@RequestBody UserDTO userDTO) {
        log.info("authorization user: {}", userDTO.getLogin());
        return ResponseEntity.ok(authenticationService.authentication(userDTO.getLogin(), userDTO.getPassword()));
    }
}

package com.task.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.servicies.AuthenticationService;
import com.task.user.servicies.UserService;
import com.task.user.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CaptchaUtil captchaUtil;

    @PostMapping("/registration/{recaptchaToken}")
    public ResponseEntity registration(@PathVariable String recaptchaToken, @RequestBody UserDTO userDTO) throws JsonProcessingException {
        log.info("registration method, recaptcha token: {}", recaptchaToken);

        if(captchaUtil.verificateCaptcha(recaptchaToken)) {
            userService.saveUser(userDTO, true);
            return ResponseEntity.ok(authenticationService.authentication(userDTO.getLogin(), userDTO.getPassword()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

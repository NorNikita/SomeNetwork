package com.task.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GoogleAuthenticationController {

    @PostMapping("/googleAuth")
    public ResponseEntity googleAuth() {
        return ResponseEntity.ok().build();
    }

}

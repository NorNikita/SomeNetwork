package com.task.user.rest;

import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.servicies.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/admin")
@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("get list of all users");
        return ResponseEntity.ok(userService.findAll());
    }

}

package com.task.user.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.user.dto.FollowerDTO;
import com.task.user.dto.SubscribeDTO;
import com.task.user.dto.SubscriptionDTO;
import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.repositories.specification.UserSpecification;
import com.task.user.servicies.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("get user with id = {}\n", id);

        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping(value = "/update/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws JsonProcessingException {
        log.info("update user with id = {}, userDTO {}\n", id, new ObjectMapper().writeValueAsString(userDTO));

        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping(value = "/getSubscriptions/{userId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(@PathVariable Long userId) {
        log.info("get subscriptions of user with Id: {}\n", userId);

        return ResponseEntity.ok(userService.getSubscriptions(userId));
    }

    @GetMapping(value = "/getFollowers/{userId}")
    public ResponseEntity<List<FollowerDTO>> getFollowers(@PathVariable Long userId) {
        log.info("get followers of user with Id: {}\n", userId);

        return ResponseEntity.ok(userService.getFollowers(userId));
    }

    @DeleteMapping(value = "/unsubscribe/{userId}/{login}")
    public ResponseEntity unsubscribe(@PathVariable Long userId, @PathVariable String login) {
        log.info("unsubscribe user with Id: {} from user with login: {}\n", userId, login);

        userService.unsubscribe(userId, login);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/deleteFollower/{userId}/{login}")
    public ResponseEntity deleteFollower(@PathVariable Long userId, @PathVariable String login) {
        log.info("user with Id: {} delete follower with login: {}\n", userId, login);

        userService.deleteFollower(userId, login);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/subscribe")
    public ResponseEntity subscribeUser(@RequestBody SubscribeDTO subscribeDTO) {
        log.info("user with id {} subscribe to user with login {}", subscribeDTO.getUserId(), subscribeDTO.getLogin());

        userService.subscribe(subscribeDTO.getUserId(), subscribeDTO.getLogin());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/searchUser/{queryLogin}")
    public ResponseEntity<List<UserDTO>> searchUser(@PathVariable @Nullable String queryLogin) throws JsonProcessingException {
        log.info("search users by query {}", queryLogin);

        List<UserDTO> body = userService.find(queryLogin);

        log.info("result search users by query {}\n", new ObjectMapper().writeValueAsString(body));
        return ResponseEntity.ok(body);
    }

    @GetMapping(value = "/getUserByLogin/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
        log.info("get user by login {}\n", login);

        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

}

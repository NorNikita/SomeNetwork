package com.task.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.user.entities.Role;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class JwtResponseDTO {

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String login;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String token;

    @JsonProperty
    private Set<Role> roles;
}


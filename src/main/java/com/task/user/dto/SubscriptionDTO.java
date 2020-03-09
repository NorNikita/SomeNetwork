package com.task.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.user.dto.use.modelmapper.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    @JsonProperty("login")
    private String login;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
}

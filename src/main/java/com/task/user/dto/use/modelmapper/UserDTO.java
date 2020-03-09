package com.task.user.dto.use.modelmapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.user.entities.Role;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO {

    @JsonProperty
    private String login;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("messages")
    private List<MessageDTO> messages;

    @JsonProperty("roles")
    private Set<Role> roles;

    @JsonProperty("likedMessages")
    private List<Long> likedMessages;

    @JsonProperty("followers")
    private List<Long> followers;

    @JsonProperty("subscriptions")
    private List<Long> subscriptions;

    public UserDTO(String login) {
        this.login = login;
    }
}

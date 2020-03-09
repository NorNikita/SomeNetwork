package com.task.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

@Getter
@Setter
public class FollowerDTO extends SubscriptionDTO {
    public FollowerDTO(String login, String firstName, String lastName) {
        super(login, firstName, lastName);
    }
}

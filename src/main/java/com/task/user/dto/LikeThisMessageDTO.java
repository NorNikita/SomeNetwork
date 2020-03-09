package com.task.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LikeThisMessageDTO {

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "messageId")
    private Long messageId;
}

package com.task.user.dto.use.modelmapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.user.dto.use.modelmapper.AbstractDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends AbstractDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("text")
    private String message;

    @JsonProperty("login")
    private String login;

    @JsonProperty("count_like")
    private Long countLike;

    @JsonProperty("createDate")
    private String createDate;

    @JsonProperty("likeThis")
    private boolean likeThis;

    @JsonProperty("image")
    private String image;

    public MessageDTO(String message, String login) {
        super();
        this.message = message;
        this.login = login;
    }

}

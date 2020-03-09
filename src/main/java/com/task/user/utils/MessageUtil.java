package com.task.user.utils;

import com.task.user.entities.Message;
import com.task.user.entities.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageUtil {

    public Optional<User> userAlreadyLikedThis(Message message, Long userId) {
        return message.getLikesOfUsers().stream().filter(user -> user.getId().equals(userId)).findFirst();
    }
}

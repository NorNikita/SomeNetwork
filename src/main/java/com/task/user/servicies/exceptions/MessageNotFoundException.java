package com.task.user.servicies.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(Long id) {
        super("Message with id = " + id + "not found!");
    }
}

package com.task.user.servicies.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with id = " + id);
    }
    public UserNotFoundException(String login) {
        super("User not found with login = " + login);
    }
}

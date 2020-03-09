package com.task.user.servicies;

import com.task.user.dto.FollowerDTO;
import com.task.user.dto.SubscriptionDTO;
import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.entities.User;
import com.task.user.servicies.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO getUser(Long id);

    User saveUser(UserDTO userDTO, boolean isNewUser);

    UserDTO updateUser(Long id, UserDTO userDTO);

    Optional<User> findByFirstName(String userName);

    Optional<User> findByLogin(String login);

    List<SubscriptionDTO> getSubscriptions(Long userId);

    List<FollowerDTO> getFollowers(Long userId);

    void unsubscribe(Long userId, String login);

    void subscribe(Long userId, String login) throws UserNotFoundException;

    void deleteFollower(Long userId, String login);

    List<UserDTO> find(String login);

    UserDTO getUserByLogin(String login);
}

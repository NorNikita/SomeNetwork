package com.task.user.servicies;

import com.task.user.dto.FollowerDTO;
import com.task.user.dto.SubscriptionDTO;
import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.entities.Role;
import com.task.user.entities.User;
import com.task.user.mapper.UserMapper;
import com.task.user.repositories.UserRepository;
import com.task.user.repositories.specification.UserSpecification;
import com.task.user.servicies.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> userMapper.toDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public User saveUser(UserDTO userDTO, boolean isNewUser) {
        User user = userMapper.toEntity(userDTO);
        if(isNewUser) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(Role.USER);
            user.setRoles(roleSet);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user;
        User updateUser = userMapper.toEntity(userDTO);
        if(id != null) {
            user = userRepository.getOne(id);
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
        } else {
            user = updateUser;
        }
        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    public Optional<User> findByFirstName(String userName) {
        return userRepository.findByFirstName(userName);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<SubscriptionDTO> getSubscriptions(Long userId) {
        return userRepository.getOne(userId).getSubscriptions().stream()
                .map(u -> new SubscriptionDTO(u.getLogin(), u.getFirstName(), u.getLastName())).collect(Collectors.toList());
    }

    @Override
    public List<FollowerDTO> getFollowers(Long userId) {
        return userRepository.getOne(userId).getFollowers().stream()
                .map(u -> new FollowerDTO(u.getLogin(), u.getFirstName(), u.getLastName())).collect(Collectors.toList());
    }

    @Override
    public void unsubscribe(Long userId, String login) {
        Optional<User> unsubscribeUser = userRepository.findByLogin(login);

        if(unsubscribeUser.isPresent()) {
            User user = userRepository.getOne(userId);
            user.getSubscriptions().remove(unsubscribeUser.get());
            userRepository.save(user);
        } else {
            log.error("User with login {} not found!", login);
        }
    }

    @Override
    public void subscribe(Long userId, String login) throws UserNotFoundException {
        Optional<User> subscribeUser = userRepository.findByLogin(login);

        if(subscribeUser.isPresent()) {
            User user = userRepository.getOne(userId);
            if(user.getSubscriptions() != null) {
                user.getSubscriptions().add(subscribeUser.get());
            } else {
                user.setSubscriptions(Arrays.asList(subscribeUser.get()));
            }
            userRepository.save(user);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    @Override
    public void deleteFollower(Long userId, String login) {
        Optional<User> deleteUser = userRepository.findByLogin(login);

        if(deleteUser.isPresent()) {
            User user = userRepository.getOne(userId);
            user.getFollowers().remove(deleteUser.get());
            userRepository.save(user);
        } else {
            log.error("User with login {} not found!", login);
        }
    }

    @Override
    public List<UserDTO> find(String login) {
        return userRepository.findAll(UserSpecification.findUserByInputString(login)).stream()
                .map(entityUser -> new UserDTO(entityUser.getLogin())).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        return userMapper.toDto(userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login)));
    }

    //    @Override
//    public List<UserDTO> findByMessage(String message) {
//        return userRepository.findAll(UserSpecification.findUserMessagesByString(message))
//                .stream().map(u -> userMapper.toDto(u)).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserDTO> findUserWithCountMessage(Long count) {
//        return userRepository.findAll(UserSpecification.findUserHaveCountMessages(count))
//                .stream().map(u -> userMapper.toDto(u)).collect(Collectors.toList());
//    }
}
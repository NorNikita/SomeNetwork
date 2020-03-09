package com.task.user.repositories;

import com.task.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByOAuthId(String id);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLogin(String login);
}

package com.task.user.repositories;

import com.task.user.entities.Message;
import com.task.user.repositories.specification.MessageSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    @Query(value = "Select * from message_user as m where m.user_id = :id ", nativeQuery = true)
    List<Message> findUserMessages(@Param("id") Long id);
}

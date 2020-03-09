package com.task.user.repositories.specification;

import com.task.user.entities.Message;
import com.task.user.entities.Message_;
import com.task.user.entities.User;
import com.task.user.entities.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;

public class MessageSpecification {

    public static Specification<Message> getMessagesFromSubscribe(Long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Message, User> messageUserJoin = root.join(Message_.user, JoinType.LEFT);
            ListJoin<User, User> join = messageUserJoin.join(User_.followers, JoinType.LEFT);
            Predicate in = join.get(User_.id).in(userId);
            return criteriaQuery.where(in).orderBy(criteriaBuilder.desc(root.get(Message_.createDate))).getRestriction();
        };
    }

}

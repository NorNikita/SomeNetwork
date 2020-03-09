package com.task.user.repositories.specification;

import com.task.user.entities.Message;
import com.task.user.entities.Message_;
import com.task.user.entities.User;
import com.task.user.entities.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;

public class UserSpecification {

    public static Specification<User> findUserByInputString(String str) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.login), (str + "%"));
    }

    public static Specification<User> findUserMessagesByString(String partMessage) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            ListJoin<User, Message> join = root.join(User_.messageList, JoinType.LEFT);
            return criteriaBuilder.like(join.get(Message_.message), ("%" + partMessage + "%"));
        };
    }

    public static Specification<User> findUserHaveCountMessages(Long c) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            ListJoin<User, Message> join = root.join(User_.messageList, JoinType.LEFT);
            Expression<Long> count = criteriaBuilder.count(join.get(Message_.id));
            criteriaQuery.groupBy(root.get(User_.id));

            return criteriaQuery.having(criteriaBuilder.le(count, c)).getRestriction();
        };
    }


}

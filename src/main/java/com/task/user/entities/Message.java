package com.task.user.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "message_user")
public class Message extends AbstractEntity{

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_user_generator"
    )
    @SequenceGenerator(
            name = "message_user_generator",
            sequenceName = "message_user_sequence",
            allocationSize = 1
    )
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "image_name")
    private String imageName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)//с CascadeType.ALL не работает удаление/иначе дублируются значения LAZY обязательно
    @JoinTable(
            name = "liked_messages_by_users",
            joinColumns = {@JoinColumn(name = "message_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> likesOfUsers = new ArrayList<>();
}

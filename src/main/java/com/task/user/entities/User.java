package com.task.user.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usr")
public class User extends AbstractEntity implements UserDetails, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "sequence_usr",
            allocationSize = 1
    )
    private Long id;
    private String login;

    @Column(name = "o_auth_id")
    private String oAuthId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    private String email;
    private String gender;
    private String picture;
    private String password;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Message> messageList = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "likesOfUsers")
    private List<Message> likedMessages = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "left_user_id", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "right_user_id", referencedColumnName = "id")
    )
    private List<User> followers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "right_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "left_user_id", referencedColumnName = "id")
    )
    private List<User> subscriptions = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
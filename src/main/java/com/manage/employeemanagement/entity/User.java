package com.manage.employeemanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "firstNameAndLastName", columnNames = {"firstName", "lastName"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Unique
    private String username;

    @Unique
    private String email;
    private Date startDate;
    private String uid;

    @OneToMany(mappedBy = "user")
    private Set<AssignedProject> assignments;


    public User(String firstName, String lastName, String username, String email, Date startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
    }

    @PrePersist
    protected void onCreate() {
        setUid(UUID.randomUUID().toString());
    }
}

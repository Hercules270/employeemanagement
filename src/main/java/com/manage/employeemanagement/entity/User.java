package com.manage.employeemanagement.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "firstNameAndLastName", columnNames = {"firstName", "lastName"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private Date startDate;
    private String userId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<AssignedProject> assignments = new HashSet<>();


    public User(String firstName, String lastName, String username, String email, Date startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
    }

    @PrePersist
    protected void onCreate() {
        setUserId(UUID.randomUUID().toString());
    }
}

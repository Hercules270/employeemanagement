package com.manage.employeemanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
    private String username;
    private String email;
    private Date startDate;

    @OneToMany(mappedBy = "user")
    private Set<AssignedProject> assignments;


    public User(String firstName, String lastName, String username, String email, Date startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
    }
}

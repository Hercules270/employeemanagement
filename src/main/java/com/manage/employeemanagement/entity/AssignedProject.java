package com.manage.employeemanagement.entity;


import jdk.jfr.Timestamp;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AssignedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Temporal(TemporalType.DATE)
    private Date date;

    public AssignedProject(User user, Project project, Date date) {
        this.user = user;
        this.project = project;
        this.date = date;
    }
}

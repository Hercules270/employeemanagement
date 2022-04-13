package com.manage.employeemanagement.entity;


import jdk.jfr.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AssignedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Temporal(TemporalType.DATE)
    private Date date;

    public AssignedProject(User user, Project project, Date date) {
        this.user = user;
        this.project = project;
        this.date = date;
    }
}

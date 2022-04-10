package com.manage.employeemanagement.controller;


import com.manage.employeemanagement.bll.ManagerBLL;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
public class ManagerController {

    private ManagerBLL managerBLL;

    @Autowired
    public ManagerController(ManagerBLL managerBLL) {
        this.managerBLL = managerBLL;
    }

    @GetMapping("/addUser")
    @RolesAllowed("manager")
    public Response addUser(@RequestBody EmployeeRegisterRequest employee) {
        managerBLL.addNewEmployee(employee);

        return Response.ok("Everything os okay").build();

    }
}

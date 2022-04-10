package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import com.manage.employeemanagement.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private Keycloak keycloak;

    private final String KEYCLOAK_REALM;

    @Autowired
    public ManagerServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String keycloak_realm) {
        this.keycloak = keycloak;
        KEYCLOAK_REALM = keycloak_realm;
    }

    @Override
    public void addNewEmployee(EmployeeRegisterRequest employee) {
        try {
            UserRepresentation userRepresentation = Converter.convert(employee);
            RealmResource realmResource = keycloak.realm("employeeManagement");
            UsersResource usersResource = realmResource.users();
            System.out.println(usersResource.count());
            Response response = usersResource.create(userRepresentation);

            System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
            System.out.println(response.getLocation());
        } catch (Exception e) {

        }

    }
}

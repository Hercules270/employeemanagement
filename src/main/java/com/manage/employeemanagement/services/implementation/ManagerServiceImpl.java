package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.enums.EmployeeRegistrationErrorEnum;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.repository.UserRepository;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import com.manage.employeemanagement.utils.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final String KEYCLOAK_REALM;

    @Autowired
    public ManagerServiceImpl(Keycloak keycloak, UserRepository userRepository, @Value("${keycloak.realm}") String keycloak_realm) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        KEYCLOAK_REALM = keycloak_realm;
    }

    @Override
    public UserRepresentation addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        UserRepresentation userRepresentation = ConverterUtils.convertEmployeeToUserRepresentation(employee);
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        UsersResource usersResource = realmResource.users();
        if(userRepository.findUserByUsername(userRepresentation.getUsername()).isPresent()) {
            log.info("User with username {} already exists", userRepresentation.getUsername());
            throw new EmployeeRegistrationException("User with username " + userRepresentation.getUsername() + " already exists", EmployeeRegistrationErrorEnum.USERNAME_ALREADY_EXISTS);
        }
        Response response = usersResource.create(userRepresentation);
        if (response.getStatus() != 201) {
            log.info("Couldn't register employee with username: {} response status: {}", userRepresentation.getUsername(), response.getStatus());
            throw new EmployeeRegistrationException("Error during registration of employee " + userRepresentation.getUsername(), EmployeeRegistrationErrorEnum.UNKNOWN_ERROR);
        }
        User newUser  = ConverterUtils.convertEmployeeToUser(employee);
        userRepository.save(newUser);
        log.info("Response: {} {}", response.getStatus(), response.getStatusInfo());
        return userRepresentation;
    }


    @Override
    public List<User> getAllEmployees(int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if(sort[0].contains(",")) {
            for (String sortOrder : sort) {
                orders.add(ConverterUtils.convertStringToOrder(sortOrder));
            }
        } else {
            orders.add(new Sort.Order(sort[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        return userRepository.findAll(pagingSort).getContent();
    }

    @Override
    public Optional<User> getEmployee(String firstName, String lastName) {
        return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
    }
}

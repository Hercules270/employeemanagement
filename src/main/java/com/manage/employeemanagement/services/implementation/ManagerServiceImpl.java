package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.enums.EmployeeRegistrationErrorEnum;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.repository.UserRepository;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import com.manage.employeemanagement.utils.ConverterUtils;
import com.manage.employeemanagement.utils.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
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
    @Transactional
    public UserRepresentation addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        isEmployeeUnique(employee);
        UserRepresentation userRepresentation = ConverterUtils.convertEmployeeToUserRepresentation(employee);
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        UsersResource usersResource = realmResource.users();
        User newUser = ConverterUtils.convertEmployeeToUser(employee);
        userRepository.save(newUser);
        Response response = usersResource.create(userRepresentation);
        if (response.getStatus() != 201) {
            log.info("Couldn't register employee with username: {} response status: {}", userRepresentation.getUsername(), response.getStatus());
            throw new EmployeeRegistrationException("Error during registration of employee " + userRepresentation.getUsername(), EmployeeRegistrationErrorEnum.UNKNOWN_ERROR);
        }
        log.info("Response: {} {}", response.getStatus(), response.getStatusInfo());
        return userRepresentation;
    }

    private void isEmployeeUnique(EmployeeRegisterRequest userRepresentation) throws EmployeeRegistrationException {
        if(userRepository.findUserByUsername(userRepresentation.getUsername()).isPresent()) {
            log.info("User with username {} already exists", userRepresentation.getUsername());
            throw new EmployeeRegistrationException("User with username " + userRepresentation.getUsername() + " already exists", EmployeeRegistrationErrorEnum.USERNAME_ALREADY_EXISTS);
        }
        if(userRepository.findUserByEmail(userRepresentation.getEmail()).isPresent()) {
            log.info("User with email {} already exists", userRepresentation.getEmail());
            throw new EmployeeRegistrationException("User with email " + userRepresentation.getEmail() + " already exists", EmployeeRegistrationErrorEnum.USERNAME_ALREADY_EXISTS);
        }
        if(userRepository.findUserByFirstNameAndLastName(userRepresentation.getFirstName(), userRepresentation.getLastName()).isPresent()) {
            log.info("User with firstName {} and lastName {} already exists", userRepresentation.getFirstName(), userRepresentation.getLastName());
            throw new EmployeeRegistrationException("User with firstName " + userRepresentation.getFirstName() + "and lastName " + userRepresentation.getLastName() + " already exists", EmployeeRegistrationErrorEnum.USERNAME_ALREADY_EXISTS);
        }
    }


    @Override
    public List<User> getAllEmployees(int page, int size, String[] sort) {
        Pageable pageAndSort = CustomUtils.getPageAndSort(page, size, sort);
        return userRepository.findAll(pageAndSort).getContent();
    }

    @Override
    public Optional<User> getEmployee(String firstName, String lastName) {
        return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
    }
}

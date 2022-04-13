package com.manage.employeemanagement.repository;

import com.manage.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> findUserByUserId(String userId);

}

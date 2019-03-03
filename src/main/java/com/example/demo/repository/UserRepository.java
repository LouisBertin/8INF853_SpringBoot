package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Find user with name optional.
     *
     * @param username the username
     * @return the optional
     */
    @Query(" select u from User u " +
            " where u.username = ?1")
    Optional<User> findUserWithName(String username);
}

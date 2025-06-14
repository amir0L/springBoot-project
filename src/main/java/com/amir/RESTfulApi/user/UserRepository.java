package com.amir.RESTfulApi.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Find users by firstname (case insensitive)
    List<User> findByFirstnameContainingIgnoreCase(String firstname);

    // Find users by lastname (case insensitive)
    List<User> findByLastnameContainingIgnoreCase(String lastname);

    // Find users by firstname or lastname (case insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByFirstnameOrLastnameContainingIgnoreCase(@Param("name") String name);

    // Find users with their matieres count
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.matieres")
    List<User> findAllWithMatieres();

    // Get users ordered by lastname, firstname
    @Query("SELECT u FROM User u ORDER BY u.lastname, u.firstname")
    List<User> findAllOrderedByName();
}
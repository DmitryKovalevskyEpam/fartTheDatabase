package com.fartone.cardservice.dao;

import com.fartone.cardservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Named method
    Optional<User> findByEmail(String email);

    // Named method with multiple results
    List<User> findBySurnameContainingIgnoreCase(String surname);

    // JPQL query
    @Query("SELECT u FROM User u WHERE u.birthDate < :date")
    List<User> findUsersBornBefore(@Param("date") LocalDate date);

    // Native SQL query
    @Query(value = "SELECT * FROM users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))",
           nativeQuery = true)
    List<User> findUsersByNameNative(@Param("name") String name);

    // Update with JPQL
    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    int updateUserEmail(@Param("id") Long id, @Param("email") String email);

    // Find multiple users by IDs
    List<User> findByIdIn(List<Long> ids);
}

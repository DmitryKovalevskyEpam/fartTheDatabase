package com.example.serserviceapp.repository;

import com.example.serserviceapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> ids);

    @Query("SELECT u FROM User u WHERE u.name = :name AND u.surname = :surname")
    List<User> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    @Query(value = "SELECT * FROM public.users WHERE id IN :ids", nativeQuery = true)
    List<User> findByIdsNative(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE public.users SET name = :name, surname = :surname WHERE id = :id", nativeQuery = true)
    int updateNameAndSurnameById(@Param("id") Long id, @Param("name") String name, @Param("surname") String surname);

    @Modifying
    @Query("DELETE FROM User u WHERE u.email = :email")
    int deleteByEmail(@Param("email") String email);
}
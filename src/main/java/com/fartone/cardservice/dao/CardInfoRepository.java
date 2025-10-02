package com.fartone.cardservice.dao;


import com.fartone.cardservice.entity.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    // Named method
    List<CardInfo> findByUserId(Long userId);

    // Named method with expiration date
    List<CardInfo> findByExpirationDateBefore(LocalDate date);

    // JPQL query with join
    @Query("SELECT c FROM CardInfo c JOIN c.user u WHERE u.email = :email")
    List<CardInfo> findCardsByUserEmail(@Param("email") String email);

    // Native SQL query
    @Query(value = "SELECT c.* FROM card_info c WHERE c.holder ILIKE %:holderName%",
           nativeQuery = true)
    List<CardInfo> findCardsByHolderNameNative(@Param("holderName") String holderName);

    // Update with JPQL
    @Modifying
    @Query("UPDATE CardInfo c SET c.holder = :holder WHERE c.id = :id")
    int updateCardHolder(@Param("id") Long id, @Param("holder") String holder);

    // Find multiple cards by IDs
    List<CardInfo> findByIdIn(List<Long> ids);

    // Cascade-aware delete by user ID
    @Modifying
    @Query("DELETE FROM CardInfo c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}

package com.example.serserviceapp.repository;

import com.example.serserviceapp.model.entity.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    List<CardInfo> findByUserId(Long userId);

    List<CardInfo> findByIdIn(List<Long> ids);

    @Query("SELECT c FROM CardInfo c WHERE c.user.email = :email")
    List<CardInfo> findByUserEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM public.card_info WHERE user_id = :userId", nativeQuery = true)
    List<CardInfo> findByUserIdNative(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE public.card_info SET number = :number, holder = :holder, expiration_date = :expirationDate WHERE id = :id", nativeQuery = true)
    int updateById(@Param("id") Long id, @Param("number") String number, @Param("holder") String holder, @Param("expirationDate") java.time.LocalDate expirationDate);

    @Modifying
    @Query("DELETE FROM CardInfo c WHERE c.user.id = :userId")
    int deleteByUserId(@Param("userId") Long userId);
}
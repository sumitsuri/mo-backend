package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.UserBookingDetailsEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookingDetailsRepo extends JpaRepository<UserBookingDetailsEntity, Long> {

    Optional<List<UserBookingDetailsEntity>> findByBookingId(@Param("bookingId") Long bookingId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.ServicesEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookingRepo extends JpaRepository<UserBookingEntity, Long> {

    Optional<List<UserBookingEntity>> findByUserIdAndStoreId(@Param("userId") Long userId, @Param("storeId") Long storeId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

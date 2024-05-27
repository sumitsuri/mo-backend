package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.UserMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMetadataRepo extends JpaRepository<UserMetadataEntity, Long> {

  //    @Query(
  //            nativeQuery = true,
  //            value =
  //                    "SELECT merchantName FROM MerchantUsers, Merchants WHERE
  // (MerchantUsers.merchantId = Merchants.id AND userId = :userId)")
  //    Optional<UserMetadataEntity> getMerchantNameByUserId(@Param("userId") Long userId);

  Optional<UserMetadataEntity> findByUserId(@Param("userId") Long userId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

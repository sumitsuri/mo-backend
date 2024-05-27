package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.jpa.ServicesEntity;
import com.Spring.SpringBootMysql.entities.jpa.StoreServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreServicesRepo extends JpaRepository<StoreServicesEntity, Long> {

    Optional<List<StoreServicesEntity>> findByStoreId(@Param("storeId") Long storeId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import com.Spring.SpringBootMysql.entities.jpa.ServiceCategoryEntity;
import com.Spring.SpringBootMysql.entities.jpa.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepo extends JpaRepository<ServicesEntity, Long> {

    Optional<List<ServicesEntity>> findByServiceCategoryId(@Param("serviceCategoryId") Long serviceCategoryId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

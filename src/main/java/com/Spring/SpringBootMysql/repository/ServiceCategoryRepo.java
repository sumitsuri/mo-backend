package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import com.Spring.SpringBootMysql.entities.jpa.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepo extends JpaRepository<ServiceCategoryEntity, Long> {

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

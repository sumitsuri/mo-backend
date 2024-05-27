package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.jpa.OrgLocationEntity;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgLocationStoreRepo extends JpaRepository<OrgLocationStoreEntity, Long> {

    Optional<List<OrgLocationStoreEntity>> findByOrgLocationId(@Param("orgLocationId") Long orgLocationId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

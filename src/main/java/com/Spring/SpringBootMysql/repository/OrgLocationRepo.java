package com.Spring.SpringBootMysql.repository;


import com.Spring.SpringBootMysql.entities.jpa.OrgLocationEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgLocationRepo extends JpaRepository<OrgLocationEntity, Long> {

    Optional<List<OrgLocationEntity>> findByOrgId(@Param("orgId") Long userId);

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

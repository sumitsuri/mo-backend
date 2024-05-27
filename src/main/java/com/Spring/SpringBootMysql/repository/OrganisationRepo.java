package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepo extends JpaRepository<OrganisationEntity, Long> {

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

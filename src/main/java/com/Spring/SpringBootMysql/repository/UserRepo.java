package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

  //  Optional<MerchantEntity> findByMerchantEmail(String merchantEmail);
}

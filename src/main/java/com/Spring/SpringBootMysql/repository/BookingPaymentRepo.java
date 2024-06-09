package com.Spring.SpringBootMysql.repository;

import com.Spring.SpringBootMysql.domains.custom.PaymentDetails;
import com.Spring.SpringBootMysql.entities.jpa.BookingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingPaymentRepo extends JpaRepository<BookingPaymentEntity, Long> {

  Optional<BookingPaymentEntity> findByUserBookingId(@Param("user_booking_id") Long userBookingId);

//  @Query(
//          nativeQuery = true,
//          value =
//                  "SELECT MerchantUsers.userId AS userId, merchantId, merchantName, merchantType, " +
//                          "MerchantUsers.role AS userRole, merchantEmail, accountAlias FROM MerchantUsers, Merchants " +
//                          "WHERE (MerchantUsers.merchantId = Merchants.id AND userId = :userId)")
//  Optional<PaymentDetails> getMerchantDetails(@Param("bookingId") Long bookingId);

//  select ubd.id,s.id,s.name,ss.price from user_booking_details ubd join store_service ss on ubd.store_service_id = ss.id
//  join service s on ss.service_id = s.id and ubd.booking_id=1;
  @Query(
          nativeQuery = true,
          value =
                  "select ubd.id bookingDetailsId, ss.id storeServiceId,s.name serviceName,ss.price storeServicePrice from user_booking_details ubd join store_service ss on ubd.store_service_id = ss.id " +
                          "join service s on ss.service_id = s.id and ubd.booking_id= :bookingId)")
  Optional<List<PaymentDetails>> getPaymentDetails(@Param("bookingId") Long bookingId);

}

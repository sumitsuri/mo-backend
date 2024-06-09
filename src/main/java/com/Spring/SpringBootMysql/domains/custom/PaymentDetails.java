package com.Spring.SpringBootMysql.domains.custom;

public interface PaymentDetails {

  /**
   * select ubd.id,s.id,s.name,ss.price from user_booking_details ubd join store_service ss on ubd.store_service_id = ss.id join service s on ss.service_id = s.id and ubd.booking_id=1;
   * @return
   */
  Long getBookingDetailsId();

  String getStoreServiceId();

  Double getStoreServicePrice();

  String getServiceName();

  String getServiceDescription();

}

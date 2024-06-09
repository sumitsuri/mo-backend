package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.OrgLocationStoreUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingUpdateRequest;

public interface UserBookingService {
  StatusMessageResponse addUserBooking(UserBookingCreateRequest userBookingCreateRequest)
      throws Exception;

  StatusMessageResponse getAllBookingByStoreAndUser(long storeId, long userId);
  StatusMessageResponse getUserBookingById(long id) throws Exception;

  StatusMessageResponse deleteUserBookingById(long id) throws Exception;

  StatusMessageResponse updateUserBookingById(
      long id, UserBookingUpdateRequest userBookingUpdateRequest) throws Exception;
}

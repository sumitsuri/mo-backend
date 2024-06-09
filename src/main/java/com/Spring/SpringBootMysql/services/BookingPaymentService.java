package com.Spring.SpringBootMysql.services;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.BookingPaymentCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingUpdateRequest;

public interface BookingPaymentService {
  StatusMessageResponse addBookingPayment(BookingPaymentCreateRequest bookingPaymentCreateRequest)
      throws Exception;

  StatusMessageResponse getPaidInvoiceByBookingId(long id) throws Exception;

  StatusMessageResponse getExpectedInvoiceByBookingId(long id) throws Exception;

}

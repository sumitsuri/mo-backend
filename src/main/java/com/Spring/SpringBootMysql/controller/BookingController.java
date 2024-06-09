package com.Spring.SpringBootMysql.controller;

import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.*;
import com.Spring.SpringBootMysql.services.BookingPaymentService;
import com.Spring.SpringBootMysql.services.OrgLocationStoreService;
import com.Spring.SpringBootMysql.services.UserBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/bookings")
@Slf4j
public class BookingController {

  @Autowired private UserBookingService userBookingService;
  @Autowired private BookingPaymentService bookingPaymentService;

  @PostMapping
  public StatusMessageResponse createBooking(
      @RequestBody @Valid UserBookingCreateRequest userBookingCreateRequest) throws Exception {
    return userBookingService.addUserBooking(userBookingCreateRequest);
  }

  @GetMapping
  public StatusMessageResponse getAllBookingsByStoreAndUser(@RequestParam(name = "storeId")long storeId,
                                                            @RequestParam(name = "userId")long userId) throws Exception {
    return userBookingService.getAllBookingByStoreAndUser(storeId, userId);
  }

  @GetMapping("/{id}")
  public StatusMessageResponse getBooking(@PathVariable long id) throws Exception {
    return userBookingService.getUserBookingById(id);
  }

  @DeleteMapping("/{id}")
  public StatusMessageResponse removeBooking(@PathVariable long id) throws Exception {
    return userBookingService.deleteUserBookingById(id);
  }

  @PutMapping("/{id}")
  public StatusMessageResponse updateBooking(
      @PathVariable long id,
      @RequestBody @Valid UserBookingUpdateRequest userBookingUpdateRequest)
      throws Exception {
    return userBookingService.updateUserBookingById(id, userBookingUpdateRequest);
  }

  @PostMapping("/{id}/payments")
  public StatusMessageResponse payBooking(
          @RequestBody @Valid BookingPaymentCreateRequest bookingPaymentCreateRequest) throws Exception {
    return bookingPaymentService.addBookingPayment(bookingPaymentCreateRequest);
  }

  @GetMapping("/{id}/payments/{paymentId}")
  public StatusMessageResponse getPaidInvoiceDetails(@PathVariable long id, @PathVariable long paymentId) throws Exception {
    return bookingPaymentService.getPaidInvoiceByBookingId(id);
  }

  @GetMapping("/{id}/payments/computed-bill")
  public StatusMessageResponse getExpectedBillDetails(@PathVariable long id) throws Exception {
    return bookingPaymentService.getExpectedInvoiceByBookingId(id);
  }

}

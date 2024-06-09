package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.custom.PaymentDetails;
import com.Spring.SpringBootMysql.domains.internal.request.BookingPaymentCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingCreateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingDetailsUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.request.UserBookingUpdateRequest;
import com.Spring.SpringBootMysql.domains.internal.response.BookingDetails;
import com.Spring.SpringBootMysql.domains.internal.response.OverallBill;
import com.Spring.SpringBootMysql.entities.jpa.BookingPaymentEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingDetailsEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.BookingPaymentRepo;
import com.Spring.SpringBootMysql.repository.UserBookingDetailsRepo;
import com.Spring.SpringBootMysql.repository.UserBookingRepo;
import com.Spring.SpringBootMysql.services.BookingPaymentService;
import com.Spring.SpringBootMysql.services.UserBookingService;
import com.Spring.SpringBootMysql.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingPaymentServiceImpl implements BookingPaymentService {

  BookingPaymentRepo bookingPaymentRepo;

  UserBookingRepo userBookingRepo;
  Utils utils;

  @Autowired
  public BookingPaymentServiceImpl(BookingPaymentRepo bookingPaymentRepo, UserBookingRepo userBookingRepo, Utils utils) {
    this.bookingPaymentRepo = bookingPaymentRepo;
    this.userBookingRepo = userBookingRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addBookingPayment(BookingPaymentCreateRequest bookingPaymentCreateRequest) throws Exception {
    OverallBill overallBill = computeOverallBill(bookingPaymentCreateRequest.getUserBookingId());
    BookingPaymentEntity bookingPaymentEntity = new BookingPaymentEntity();
    bookingPaymentEntity.setUserBookingId(bookingPaymentCreateRequest.getUserBookingId());
    bookingPaymentEntity.setDiscount(bookingPaymentCreateRequest.getDiscount());
    bookingPaymentEntity.setPaymentMethod(bookingPaymentCreateRequest.getPaymentMethod());
    bookingPaymentEntity.setTotalAmount(overallBill.getTotalAmount());
    bookingPaymentEntity.setPaidAmount(overallBill.getTotalAmount());
    if((bookingPaymentCreateRequest.getDiscount() != null) && (bookingPaymentCreateRequest.getDiscount().doubleValue() > 0)){
      bookingPaymentEntity.setPaidAmount(overallBill.getTotalAmount() - bookingPaymentCreateRequest.getDiscount());
    }
    bookingPaymentEntity.setAddedBy(-1);
    bookingPaymentEntity.setUpdatedBy(-1);
    bookingPaymentRepo.save(bookingPaymentEntity);
    return StatusMessageResponse.builder()
            .data(bookingPaymentEntity)
            .code(HttpStatus.OK.value())
            .message("Booking payment done successfully")
            .build();
  }



  @Override
  public StatusMessageResponse getPaidInvoiceByBookingId(long id) throws Exception {
    Optional<BookingPaymentEntity> optionalBookingPaymentEntity = bookingPaymentRepo.findById(id);
    if (!optionalBookingPaymentEntity.isPresent()) {
      throw new ResourceNotFoundException("Payment with boking id: " + id + " not found");
    }
    return StatusMessageResponse.builder()
            .data(optionalBookingPaymentEntity.get())
            .code(HttpStatus.OK.value())
            .message("Booking payment details fetched")
            .build();
  }

  @Override
  public StatusMessageResponse getExpectedInvoiceByBookingId(long id) throws Exception {
    return StatusMessageResponse.builder()
            .data(computeOverallBill(id))
            .code(HttpStatus.OK.value())
            .message("Final bill details")
            .build();
  }

  private OverallBill computeOverallBill(long id) throws Exception {
    Optional<UserBookingEntity> optionalUserBookingEntity = userBookingRepo.findById(id);
    if (!optionalUserBookingEntity.isPresent()) {
      throw new ResourceNotFoundException("Booking with id: " + id + " not found");
    }
    Optional<List<PaymentDetails>> optionalPaymentDetails = bookingPaymentRepo.getPaymentDetails(id);
    if (!optionalPaymentDetails.isPresent()) {
      throw new ResourceNotFoundException("Payment details for Booking with id: " + id + " not found");
    }
    List<PaymentDetails> paymentDetailList = optionalPaymentDetails.get();

    double finalAmount = 0;
    for(PaymentDetails paymentDetails: paymentDetailList){
      finalAmount += paymentDetails.getStoreServicePrice();
    }
    return OverallBill.builder()
            .bookingId(id)
            .totalAmount(finalAmount)
            .paymentDetails(paymentDetailList)
            .build();
  }
}

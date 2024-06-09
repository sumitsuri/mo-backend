package com.Spring.SpringBootMysql.services.impl;

import com.Spring.SpringBootMysql.constants.Constants;
import com.Spring.SpringBootMysql.domains.common.response.StatusMessageResponse;
import com.Spring.SpringBootMysql.domains.internal.request.*;
import com.Spring.SpringBootMysql.domains.internal.response.BookingDetails;
import com.Spring.SpringBootMysql.entities.jpa.OrgLocationStoreEntity;
import com.Spring.SpringBootMysql.entities.jpa.OrganisationEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingDetailsEntity;
import com.Spring.SpringBootMysql.entities.jpa.UserBookingEntity;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import com.Spring.SpringBootMysql.repository.OrganisationRepo;
import com.Spring.SpringBootMysql.repository.UserBookingDetailsRepo;
import com.Spring.SpringBootMysql.repository.UserBookingRepo;
import com.Spring.SpringBootMysql.services.OrganisationService;
import com.Spring.SpringBootMysql.services.UserBookingService;
import com.Spring.SpringBootMysql.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserBookingServiceImpl implements UserBookingService {

  UserBookingRepo userBookingRepo;
  UserBookingDetailsRepo userBookingDetailsRepo;
  Utils utils;

  @Autowired
  public UserBookingServiceImpl(UserBookingRepo userBookingRepo, UserBookingDetailsRepo userBookingDetailsRepo, Utils utils) {
    this.userBookingRepo = userBookingRepo;
    this.userBookingDetailsRepo = userBookingDetailsRepo;
    this.utils = utils;
  }

  @Transactional
  @Override
  public StatusMessageResponse addUserBooking(UserBookingCreateRequest userBookingCreateRequest) throws Exception {
    UserBookingEntity userBookingEntity = new UserBookingEntity();
    userBookingEntity.setUserId(userBookingCreateRequest.getUserId());
    userBookingEntity.setBookingStartTime(userBookingCreateRequest.getBookingStartTime());
    userBookingEntity.setStoreId(userBookingCreateRequest.getStoreId());
    userBookingEntity.setNotes(userBookingCreateRequest.getNotes());
    userBookingEntity.setStatus(Constants.BookingStatus.REQUESTED);
    userBookingEntity.setAddedBy(-1);
    userBookingEntity.setUpdatedBy(-1);
    userBookingRepo.save(userBookingEntity);

    //add service-details to booking
    List<UserBookingDetailsEntity> userBookingDetailsEntities = new ArrayList<>();
    userBookingCreateRequest
            .getUserBookingDetailsCreateRequestList()
            .stream()
            .forEach(userBookingDetailsCreateRequest -> {
              UserBookingDetailsEntity userBookingDetailsEntity = new UserBookingDetailsEntity();
              userBookingDetailsEntity.setBookingId(userBookingEntity.getId());
              userBookingDetailsEntity.setStoreServiceId(userBookingDetailsCreateRequest.getStoreServiceId());
              userBookingDetailsEntity.setStatus(Constants.BookingStatus.REQUESTED);
              userBookingDetailsEntity.setAddedBy(-1);
              userBookingDetailsEntity.setUpdatedBy(-1);
              userBookingDetailsEntities.add(userBookingDetailsEntity);
            });
    userBookingDetailsRepo.saveAll(userBookingDetailsEntities);

    return StatusMessageResponse.builder()
            .data(userBookingEntity)
            .code(HttpStatus.OK.value())
            .message("Booking requested successfully")
            .build();
  }

  @Override
  public StatusMessageResponse getAllBookingByStoreAndUser(long storeId, long userId) {
    Optional<List<UserBookingEntity>> optionalUserBookingEntities = userBookingRepo.findByUserIdAndStoreId(userId, storeId);
    List<UserBookingEntity> userBookingEntities = new ArrayList<>();
    if(optionalUserBookingEntities.isPresent()){
      userBookingEntities = optionalUserBookingEntities.get();
    }
    List<BookingDetails> bookingDetails = new ArrayList<>();
    userBookingEntities.forEach(userBookingEntity -> {
      bookingDetails.add(getBookingDetails(userBookingEntity));
    });
    return StatusMessageResponse.builder()
            .data(bookingDetails)
            .code(HttpStatus.OK.value())
            .message("Booking details fetched successfully")
            .build();
  }

  private BookingDetails getBookingDetails(UserBookingEntity userBookingEntity){

    List<UserBookingDetailsEntity> userBookingDetailsEntities = new ArrayList<>();
    Optional<List<UserBookingDetailsEntity>> optionalUserBookingDetailsEntities
            = userBookingDetailsRepo.findByBookingId(userBookingEntity.getId());
    if (optionalUserBookingDetailsEntities.isPresent()) {
      userBookingDetailsEntities = optionalUserBookingDetailsEntities.get();
    }

    List<BookingDetails.BookingLineItem> bookingLineItems = new ArrayList<>();
    userBookingDetailsEntities.stream()
            .forEach(userBookingDetailsEntity -> {
              bookingLineItems.add(BookingDetails.BookingLineItem.builder()
                      .id(userBookingDetailsEntity.getId())
                      .storeServiceId(userBookingDetailsEntity.getStoreServiceId())
                      .status(userBookingDetailsEntity.getStatus())
                      .build());
            });

    BookingDetails bookingDetails = BookingDetails.builder()
            .storeId(userBookingEntity.getStoreId())
            .notes(userBookingEntity.getNotes())
            .addedOn(userBookingEntity.getAddedOn())
            .status(userBookingEntity.getStatus())
            .bookingStartTime(userBookingEntity.getBookingStartTime())
            .bookingLineItems(bookingLineItems)
            .build();
    return bookingDetails;
  }


  @Override
  public StatusMessageResponse getUserBookingById(long id) throws Exception {
    Optional<UserBookingEntity> optionalUserBookingEntity = userBookingRepo.findById(id);
    if (!optionalUserBookingEntity.isPresent()) {
      throw new ResourceNotFoundException("Booking with id: " + id + " not found");
    }
    //Get booking details.
    return StatusMessageResponse.builder()
            .data(getBookingDetails(optionalUserBookingEntity.get()))
            .code(HttpStatus.OK.value())
            .message("Organisation details fetched")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse deleteUserBookingById(long id) throws Exception {
    Optional<UserBookingEntity> optionalUserBookingEntity = userBookingRepo.findById(id);
    if (!optionalUserBookingEntity.isPresent()) {
      throw new ResourceNotFoundException("Booking with id: " + id + " not found");
    }
    UserBookingEntity userBookingEntity = optionalUserBookingEntity.get();
    userBookingEntity.setStatus(Constants.BookingStatus.CANCELLED);

    userBookingRepo.save(userBookingEntity);
    return StatusMessageResponse.builder()
            .data(userBookingEntity)
            .code(HttpStatus.OK.value())
            .message("Booking cancelled successfully")
            .build();
  }

  @Transactional
  @Override
  public StatusMessageResponse updateUserBookingById(long id, UserBookingUpdateRequest userBookingUpdateRequest) throws Exception {
    Optional<UserBookingEntity> optionalUserBookingEntity = userBookingRepo.findById(id);
    if (!optionalUserBookingEntity.isPresent()) {
      throw new ResourceNotFoundException("Booking with id: " + id + " not found");
    }
    UserBookingEntity userBookingEntity = optionalUserBookingEntity.get();
    userBookingEntity.setNotes(userBookingUpdateRequest.getNotes());
    userBookingEntity.setBookingStartTime(userBookingUpdateRequest.getBookingStartTime());
    userBookingRepo.save(userBookingEntity);

    List<UserBookingDetailsUpdateRequest> userBookingDetailsUpdateRequests = userBookingUpdateRequest.getUserBookingDetailsUpdateRequests();

    List<UserBookingDetailsEntity> userBookingDetailsEntities =
            userBookingDetailsRepo.findByBookingId(userBookingEntity.getId()).get();

    Map<Long, UserBookingDetailsEntity> userBookingDetailsMap = userBookingDetailsEntities.stream()
            .collect(Collectors.toMap(UserBookingDetailsEntity::getId, Function.identity()));

    List<UserBookingDetailsEntity> updatedUserBookingDetailsEntities = new ArrayList<>();
    userBookingDetailsUpdateRequests.stream()
            .forEach(userBookingDetailsUpdateRequest -> {
              if(userBookingDetailsMap.containsKey(userBookingDetailsUpdateRequest.getUserBookingDetailsId())){
                UserBookingDetailsEntity userBookingDetailsEntity = userBookingDetailsMap.get(userBookingDetailsUpdateRequest.getUserBookingDetailsId());
                userBookingDetailsEntity.setStatus(userBookingDetailsUpdateRequest.getStatus());
                updatedUserBookingDetailsEntities.add(userBookingDetailsEntity);
              }else{
                UserBookingDetailsEntity userBookingDetailsEntity = new UserBookingDetailsEntity();
                userBookingDetailsEntity.setBookingId(userBookingEntity.getId());
                userBookingDetailsEntity.setStoreServiceId(userBookingDetailsUpdateRequest.getStoreServiceId());
                userBookingDetailsEntity.setStatus(Constants.BookingStatus.REQUESTED);
                userBookingDetailsEntity.setAddedBy(-1);
                userBookingDetailsEntity.setUpdatedBy(-1);
                updatedUserBookingDetailsEntities.add(userBookingDetailsEntity);
              }
            });

    userBookingDetailsRepo.saveAll(userBookingDetailsEntities);

    return StatusMessageResponse.builder()
            .data(userBookingEntity)
            .code(HttpStatus.OK.value())
            .message("Booking updated successfully")
            .build();
  }
}

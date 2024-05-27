/*
 * Copyright Cashfree(c) 2019.
 */

package com.Spring.SpringBootMysql.constants;

import org.springframework.http.HttpStatus;

public class ErrorConstants {
  public static final String ENDPOINT_NOT_FOUND = "ENDPOINT_NOT_FOUND";
  public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
  public static final String RESOURCE_EXIST = "RESOURCE_EXIST";
  public static final String REQUEST_INVALID = "REQUEST_INVALID";
  public static final String INVALID_HTTPMEDIA = "INVALID_HTTPMEDIA";
  public static final String INVALID_HTTPMETHOD = "INVALID_HTTPMETHOD";
  public static final String UNKNOWN_RUNTIME_EXCEPTION = "UNKNOWN_RUNTIME_EXCEPTION";
  public static final String UNKNOWN_EXCEPTION = "UNKNOWN_EXCEPTION";
  public static final String BAD_REQUEST = "BAD_REQUEST";
  public static final String UNAUTHORIZED = "UNAUTHORIZED";
  public static final String FORBIDDEN = "FORBIDDEN";
  public static final String CONFLICT = "CONFLICT";
  public static final String NOTIFICATION_BAD_REQUEST = "NOTIFICATION_BAD_REQUEST";
  public static final String INVALID_TOKEN = "INVALID_TOKEN";
  public static final String CREATE_MERCHANT_TEST_ACCOUNT_ERROR =
      "CREATE_MERCHANT_TEST_ACCOUNT_ERROR";
  public static final String AWS_UPLOAD_EXCEPTION = "AWS_UPLOAD_EXCEPTION";
  public static final String INVALID_ALIAS_PERM_CODE = "INVALID_ALIAS_PERM_CODE";
  public static final String USER_NOT_AUTHORIZED = "USER_NOT_AUTHORIZED";
  public static final String INVALID_MERCHANT_CONFIG_NAME = "INVALID_MERCHANT_CONFIG_NAME";
  public static final String MERCHANT_CONFIG_UPSERT_EXCEPTION = "MERCHANT_CONFIG_UPSERT_EXCEPTION";
  public static final String JWT_TOKEN_INVALID = "JWT_TOKEN_INVALID";
  public static final String JWT_TOKEN_EXPIRED = "JWT_TOKEN_EXPIRED";
  public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
  public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
  public static final String USER_ACCOUNT_LOCKED = "USER_ACCOUNT_LOCKED";
  public static final String REFRESH_TOKEN_EXPIRED = "REFRESH_TOKEN_EXPIRED";
  public static final String VALID_AUTH_TOKEN_NOT_FOUND = "VALID_AUTH_TOKEN_NOT_FOUND";
  public static final String ACCESS_TOKEN_NOT_EXPIRED = "ACCESS_TOKEN_NOT_EXPIRED";
  public static final String SMS_ERROR = "SMS_SENDING_FAILED";
  public static final String INVALID_OTP = "INVALID_OTP";
  public static final String EXPIRED_OTP = "EXPIRED_OTP";
  public static final String FRESHSALES_CLIENT_EXCEPTION = "FRESHSALES_CLIENT_EXCEPTION";
  public static final String MERCHANT_SERVICE_CLIENT_EXCEPTION =
      "MERCHANT_SERVICE_CLIENT_EXCEPTION";
  public static final String MERCHANT_CHANNEL_NOT_FOUND = "MERCHANT_CHANNEL_NOT_FOUND";
  public static final String USERNAME_ALREADY_EXISTS = "USERNAME_ALREADY_EXISTS";
  public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
  public static final String INVALID_EMAIL = "INVALID_EMAIL";
  public static final String INVALID_G2FA_PIN = "INVALID_G2FA_PIN";
  public static final String SALESFORCE_LEAD_ALREADY_EXISTS = "SALESFORCE_LEAD_ALREADY_EXISTS";
  public static final String SALESFORCE_LEAD_BAD_REQUEST = "SALESFORCE_LEAD_BAD_REQUEST";
  public static final String FRESH_DESK_CONTACT_BAD_REQUEST = "FRESH_DESK_CONTACT_BAD_REQUEST";
  public static final String FRESH_DESK_CONTACT_EXISTS = "FRESH_DESK_CONTACT_EXISTS";
  public static final String ADMIN_SERVICE_EXCEPTION = "ADMIN_SERVICE_EXCEPTION";
  public static final String MULTIPLE_ACTIVE_TnCs_EXISTS_FOR_A_TnCTYPE =
      "MULTIPLE_ACTIVE_TnCs_EXISTS_FOR_A_TnCTYPE";
  public static final String SALESFORCE_INTEGRATION_EXCEPTION = "Salesforce integration exception";
  public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
  public static final String INTERNAL_SERVER_ERROR_TITLE = "INTERNAL_SERVER_ERROR";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE =
      "Something went wrong on our server while processing your request. Please retry after some time.";
  public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE ";
  public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
  public static final String COMMON_ADMIN_SVC_EXCEPTION = "COMMON_ADMIN_SVC_EXCEPTION";
  public static final String NOT_ALLOWED = "NOT_ALLOWED";
  public static final String INVALID_MERCHANT_ID = "INVALID_MERCHANT_ID";
  public static final String ERROR = "ERROR";
  public static final String ALIAS_NOT_FOUND_MESSAGE = "Merchant Alias not found";
  public static final String UNSUPPORTED_AUTHTYPE = "UNSUPPORTED_AUTHTYPE";
  public static final String UNSUPPORTED_OTPCHANNEL = "UNSUPPORTED_OTPCHANNEL";
  public static final String EMAIL_ALREADY_VERIFIED = "Email has already been verified";
  public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
  public static final String INVALID_MERCHANT_ID_MSG = "Invalid Merchant ID";
  public static final String ACCOUNT_CANNOT_BE_NULL =
      "accountId, baasAccountId, lendingAnchorId and productCode, all cannot be null";
  public static final String UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY";
  public static final String OTP_SEND_FAILURE = "Failed to send OTP";
  public static final String OTP_SEND_SUCCESS = "Otp Sent successfully.";
  public static final String SAME_IP_ERROR_MSG = "Suspicious activity detected from this network";
  public static final String ACCESS_BLOCKED = "Access restricted in your region";
  public enum ErrorType {
    USER_NOT_FOUND(
        ERROR,
        ErrorConstants.USER_NOT_FOUND,
        ErrorConstants.USER_NOT_FOUND,
        HttpStatus.NOT_FOUND.value()),
    RESOURCE_NOT_FOUND(
        ErrorConstants.ERROR,
        ErrorConstants.RESOURCE_NOT_FOUND,
        ErrorConstants.RESOURCE_NOT_FOUND,
        HttpStatus.NOT_FOUND.value()),
    BAD_REQUEST(
        ErrorConstants.ERROR,
        ErrorConstants.BAD_REQUEST,
        ErrorConstants.BAD_REQUEST,
        HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR(
        ErrorConstants.ERROR,
        ErrorConstants.INTERNAL_SERVER_ERROR_TITLE,
        ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE,
        HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_CREDENTIALS(
        ErrorConstants.ERROR,
        ErrorConstants.UNAUTHORIZED,
        ErrorConstants.UNAUTHORIZED,
        HttpStatus.UNAUTHORIZED.value()),
    USER_NOT_AUTHORISED(
        ErrorConstants.ERROR,
        ErrorConstants.USER_NOT_AUTHORIZED,
        ErrorConstants.USER_NOT_FOUND,
        HttpStatus.FORBIDDEN.value()),

    CAPTCHA_UNAUTHORIZED(ErrorConstants.ERROR, null, null, HttpStatus.UNAUTHORIZED.value()),
    EMAIL_NOTIFICATION_ERROR(
        ErrorConstants.ERROR,
        ErrorConstants.NOTIFICATION_BAD_REQUEST,
        ErrorConstants.NOTIFICATION_BAD_REQUEST,
        HttpStatus.BAD_REQUEST.value()),
    INVALID_AUTH_TYPE(
        ERROR, UNSUPPORTED_AUTHTYPE, UNSUPPORTED_AUTHTYPE, HttpStatus.BAD_REQUEST.value()),
    INVALID_OTP_CHANNEL(
        ERROR, UNSUPPORTED_OTPCHANNEL, UNSUPPORTED_OTPCHANNEL, HttpStatus.BAD_REQUEST.value()),
    INVALID_EMAIL_EXCEPTION(ERROR, INVALID_EMAIL, INVALID_EMAIL, HttpStatus.BAD_REQUEST.value()),
    USERNAME_ALREADY_EXSITS_EXCETPION(
        ERROR, USERNAME_ALREADY_EXISTS, USERNAME_ALREADY_EXISTS, HttpStatus.CONTINUE.value()),
    EMAIL_ALREADY_EXISTS_EXCEPTION(
        ERROR, EMAIL_ALREADY_EXISTS, EMAIL_ALREADY_EXISTS, HttpStatus.CONTINUE.value()),
    INVALID_OTP_EXCEPTION(ERROR, INVALID_OTP, INVALID_OTP, HttpStatus.UNAUTHORIZED.value()),
    // Adding this Enum to make title and status compatible with the V2 Exception
    GENERIC_CLIENT_EXCEPTION_V1(ERROR, "", "", HttpStatus.BAD_REQUEST.value()),
    INVALID_ALIAS_PERMISSION_CODE(
        ERROR, INVALID_ALIAS_PERM_CODE, INVALID_ALIAS_PERM_CODE, HttpStatus.FORBIDDEN.value()),
    RESOURCE_ALREADY_EXISTS(ERROR, RESOURCE_EXIST, RESOURCE_EXIST, HttpStatus.CONFLICT.value()),
    UNPROCESSABLE_ENTITY_EXCEPTION(
        ERROR, UNPROCESSABLE_ENTITY, UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value()),
    JWT_TOKEN_INVALID_EXCEPTION(
        ERROR, JWT_TOKEN_INVALID, JWT_TOKEN_INVALID, HttpStatus.UNAUTHORIZED.value()),
    VALID_AUTH_TOKEN_NOT_FOUND_EXCEPTION(
        ERROR,
        VALID_AUTH_TOKEN_NOT_FOUND,
        VALID_AUTH_TOKEN_NOT_FOUND,
        HttpStatus.NOT_FOUND.value()),
    REFRESH_TOKEN_EXPIRED_EXCEPTION(
        ERROR, REFRESH_TOKEN_EXPIRED, REFRESH_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_NOT_EXPIRED_EXCEPTION(
        ERROR, ACCESS_TOKEN_NOT_EXPIRED, ACCESS_TOKEN_NOT_EXPIRED, HttpStatus.BAD_REQUEST.value());
    public final String errorStatus;
    public final String errorTitle;
    public final String errorMessage;
    public final int httpStatus;

    ErrorType(String errorStatus, String errorTitle, String errorMessage, int httpStatus) {
      this.errorStatus = errorStatus;
      this.errorTitle = errorTitle;
      this.errorMessage = errorMessage;
      this.httpStatus = httpStatus;
    }
  }
}

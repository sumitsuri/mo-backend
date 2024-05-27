package com.Spring.SpringBootMysql.aspects;

import com.Spring.SpringBootMysql.constants.ErrorConstants;
import com.Spring.SpringBootMysql.exceptions.ResourceExistException;
import com.Spring.SpringBootMysql.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Using aspect and custom-annotation instead of interceptor:
 * https://medium.com/@dudkamv/custom-java-annotation-ideas-with-spring-aop-6e069df1ce7b
 */
@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionControllerAdvice {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResult onConstraintViolationException(ConstraintViolationException exception) {
    ErrorResult errorResult = new ErrorResult();
    errorResult.setMessage("Constraint violation, check the request body and try again.");
    errorResult.setStatus("ERROR");
    for (ConstraintViolation fieldError : exception.getConstraintViolations()) {
      errorResult
          .getFieldErrors()
          .add(
              new FieldValidationError(
                  fieldError.getPropertyPath().toString().split("\\.")[1],
                  fieldError.getMessage()));
    }
    return errorResult;
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ProblemDetail onBadCredentials(BadCredentialsException e) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    problemDetail.setTitle(ErrorConstants.UNAUTHORIZED);
    return problemDetail;
    //
    //    problemDetail.setTitle("Something Not Found");
    //    problemDetail.setType(URI.create("https://example.com/something-not-found"));
    //    problemDetail.setProperty("custom_property", "New Message");
    //    return problemDetail;
    //    log.info(e.toString());
    //    return Problem.builder()
    //        .withStatus(Status.UNAUTHORIZED)
    //        .withTitle(ErrorConstants.UNAUTHORIZED)
    //        .withDetail(e.getMessage())
    //        .build();
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail onResourceNotFound(ResourceNotFoundException e) {
    log.info(e.toString());
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setTitle(ErrorConstants.RESOURCE_NOT_FOUND);
    return problemDetail;
    //    return Problem.builder()
    //            .withTitle(ErrorConstants.RESOURCE_NOT_FOUND)
    //            .withDetail(e.getMessage())
    //            .build();
  }

  //
  //  @ExceptionHandler(UsernameNotFoundException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onUsernameNotFound(UsernameNotFoundException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //            .withStatus(Status.UNAUTHORIZED)
  //            .withTitle(ErrorConstants.UNAUTHORIZED)
  //            .withDetail(e.getMessage())
  //            .build();
  //  }
  //
  //  @ExceptionHandler(LockedException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onLocked(LockedException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.UNAUTHORIZED)
  //        .withTitle(ErrorConstants.UNAUTHORIZED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
  //  @ResponseStatus(HttpStatus.CONFLICT)
  //  public Problem onLocked(UserAlreadyExistAuthenticationException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.CONFLICT)
  //        .withTitle(ErrorConstants.RESOURCE_EXIST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  @ExceptionHandler(ResourceExistException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail onResourceExist(ResourceExistException e) {
    log.info(e.toString());
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setTitle(ErrorConstants.USER_NOT_FOUND);
    return problemDetail;
  }

  @Data
  @NoArgsConstructor
  static class ErrorResult {
    private final List<FieldValidationError> fieldErrors = new ArrayList<>();
    private String status;
    private String message;

    ErrorResult(String field, String message) {
      this.fieldErrors.add(new FieldValidationError(field, message));
    }
  }

  @Getter
  @AllArgsConstructor
  static class FieldValidationError {
    private String field;
    private String message;
  }
  //
  //  @ExceptionHandler(NoHandlerFoundException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public Problem onHandlerNotFound(NoHandlerFoundException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.NOT_FOUND)
  //        .withTitle(ErrorConstants.ENDPOINT_NOT_FOUND)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(RuntimeException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onUnknownRuntimeException(RuntimeException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withStatus(Status.INTERNAL_SERVER_ERROR)
  //        .withTitle(ErrorConstants.UNKNOWN_RUNTIME_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(Exception.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onUnknownException(Exception e) {
  //    log.error(e.toString(), e);
  //    return Problem.builder()
  //        .withStatus(Status.INTERNAL_SERVER_ERROR)
  //        .withTitle(ErrorConstants.UNKNOWN_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UnknownException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onCustomUnknownException(UnknownException e) {
  //    log.error(e.toString(), e);
  //    return Problem.builder()
  //        .withStatus(Status.INTERNAL_SERVER_ERROR)
  //        .withTitle(ErrorConstants.UNKNOWN_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(MethodArgumentNotValidException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidArgumentException(MethodArgumentNotValidException e) {
  //    log.error(e.toString(), e);
  //    return Problem.builder()
  //        .withStatus(Status.BAD_REQUEST)
  //        .withTitle(ErrorConstants.BAD_REQUEST)
  //        .withDetail(
  //            e.getBindingResult().getAllErrors().stream()
  //                .map(
  //                    (Function<ObjectError, Object>)
  //                        DefaultMessageSourceResolvable::getDefaultMessage)
  //                .collect(Collectors.toList())
  //                .toString())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidKeyException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidKeyException(InvalidKeyException e) {
  //    log.error(e.toString(), e);
  //    return Problem.builder()
  //        .withStatus(Status.BAD_REQUEST)
  //        .withTitle(ErrorConstants.BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(ResolutionException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public Problem onResolutionException(ResolutionException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.NOT_FOUND)
  //        .withTitle(ErrorConstants.RESOURCE_NOT_FOUND)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(SignUpBlacklistedException.class)
  //  @ResponseStatus(HttpStatus.FORBIDDEN)
  //  public Problem onBlacklistException(SignUpBlacklistedException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.FORBIDDEN)
  //        .withTitle(ErrorConstants.FORBIDDEN)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(ResourceAlreadyExistsException.class)
  //  @ResponseStatus(HttpStatus.CONFLICT)
  //  public Problem onResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withStatus(Status.CONFLICT)
  //        .withTitle(ErrorConstants.CONFLICT)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(EmailNotificationException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onEmailNotificationException(EmailNotificationException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.NOTIFICATION_BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidAccountException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidAccountException(InvalidAccountException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withStatus(Status.BAD_REQUEST)
  //        .withTitle(ErrorConstants.BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //

  //
  //  @ExceptionHandler(InvalidTokenException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onInvalidTokenException(InvalidTokenException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_TOKEN)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(CreateMerchantTestAccountException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onCreateMerchantTestAccountException(CreateMerchantTestAccountException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.CREATE_MERCHANT_TEST_ACCOUNT_ERROR)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(AWSUploadException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onAWSUploadException(AWSUploadException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.AWS_UPLOAD_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UserNotAuthorizedException.class)
  //  @ResponseStatus(HttpStatus.FORBIDDEN)
  //  public Problem onUserNotAuthorizedException(UserNotAuthorizedException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.USER_NOT_AUTHORIZED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidMerchantConfigNameException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidMerchantConfigNameException(InvalidMerchantConfigNameException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_MERCHANT_CONFIG_NAME)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(JwtTokenInvalidException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onJwtTokenInvalidException(JwtTokenInvalidException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.JWT_TOKEN_INVALID)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(JwtTokenExpiredException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onJwtTokenExpiredException(JwtTokenExpiredException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.JWT_TOKEN_EXPIRED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidAliasPermissionCodeException.class)
  //  @ResponseStatus(HttpStatus.FORBIDDEN)
  //  public Problem onInvalidAliasPermissionCodeException(InvalidAliasPermissionCodeException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_ALIAS_PERM_CODE)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(MerchantConfigUpsertException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onMerchantConfigUpsertException(MerchantConfigUpsertException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.MERCHANT_CONFIG_UPSERT_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidCredentialsException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onInvalidCredentialsException(InvalidCredentialsException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_CREDENTIALS)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UserNotFoundException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public Problem onUserNotFoundException(UserNotFoundException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.USER_NOT_FOUND)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UserAccountLockedException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onUserAccountLockedException(UserAccountLockedException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.USER_ACCOUNT_LOCKED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(RefreshTokenExpiredException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onRefreshTokenExpiredException(RefreshTokenExpiredException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.REFRESH_TOKEN_EXPIRED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(SMSNotificationException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onBadRequestForNotificationClient(SMSNotificationException e) {
  //    log.error(e.toString());
  //    return
  // Problem.builder().withTitle(ErrorConstants.SMS_ERROR).withDetail(e.getMessage()).build();
  //  }
  //
  //  @ExceptionHandler(PhoneNumberWrongException.class)
  //  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  //  public Problem onBadRequestForPhoneNumber(PhoneNumberWrongException e) {
  //    log.error(e.toString());
  //    return
  // Problem.builder().withTitle(ErrorConstants.SMS_ERROR).withDetail(e.getMessage()).build();
  //  }
  //
  //  @ExceptionHandler(InvalidOTP.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onInvalidOTP(InvalidOTP e) {
  //    log.error(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_OTP)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(ValidAuthTokenNotFoundException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public Problem onValidAuthTokenNotFoundException(ValidAuthTokenNotFoundException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.VALID_AUTH_TOKEN_NOT_FOUND)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(ExpiredOTP.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onExpiredOTP(ExpiredOTP e) {
  //    log.error(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.EXPIRED_OTP)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(AccessTokenNotExpiredException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onAccessTokenNotExpiredException(AccessTokenNotExpiredException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.ACCESS_TOKEN_NOT_EXPIRED)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(FreshSalesClientException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onFreshSalesClientException(FreshSalesClientException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.FRESHSALES_CLIENT_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(MerchantServiceClientException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onMerchantServiceClientException(MerchantServiceClientException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.MERCHANT_SERVICE_CLIENT_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(MerchantChannelNotFoundException.class)
  //  @ResponseStatus(HttpStatus.NOT_FOUND)
  //  public Problem onMerchantChannelNotFoundException(MerchantChannelNotFoundException e) {
  //    log.error(e.getMessage());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.MERCHANT_CHANNEL_NOT_FOUND)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(UsernameAlreadyExistsException.class)
  //  @ResponseStatus(HttpStatus.CONFLICT)
  //  public Problem onUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
  //    log.error(e.getMessage());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.USERNAME_ALREADY_EXISTS)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(EmailAlreadyExistsException.class)
  //  @ResponseStatus(HttpStatus.CONFLICT)
  //  public Problem onEmailAlreadyExistsException(EmailAlreadyExistsException e) {
  //    log.error(e.getMessage());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.EMAIL_ALREADY_EXISTS)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidEmailException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidEmailException(InvalidEmailException e) {
  //    log.error(e.getMessage());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_EMAIL)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidG2faPinException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public Problem onInvalidG2faPinException(InvalidG2faPinException e) {
  //    log.error(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INVALID_G2FA_PIN)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(InvalidRequestException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onInvalidRequest(InvalidRequestException e) {
  //    return Problem.builder()
  //        .withStatus(Status.BAD_REQUEST)
  //        .withTitle(ErrorConstants.BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(SalesforceLeadAlreadyExistsException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onSalesforceLeadAlreadyExistsException(SalesforceLeadAlreadyExistsException e)
  // {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.SALESFORCE_LEAD_ALREADY_EXISTS)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(SalesforceLeadBadRequestException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onSalesforceLeadBadRequestException(SalesforceLeadBadRequestException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.SALESFORCE_LEAD_BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(FreshDeskBadRequestException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onFreshDeskBadRequestException(FreshDeskBadRequestException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.FRESH_DESK_CONTACT_BAD_REQUEST)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(FreshDeskContactExistException.class)
  //  @ResponseStatus(HttpStatus.CONFLICT)
  //  public Problem onFreshDeskContactExistsException(FreshDeskContactExistException e) {
  //    log.error(e.getMessage(), e);
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.FRESH_DESK_CONTACT_EXISTS)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(AdminServiceException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onAdminServiceException(AdminServiceException e) {
  //    log.error(e.toString(), e);
  //    return Problem.builder()
  //        .withStatus(Status.BAD_REQUEST)
  //        .withTitle(ErrorConstants.ADMIN_SERVICE_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(MultipleActiveTnCsExistsForTnCType.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem onMultipleActiveTnCsExistsForTnCType(MultipleActiveTnCsExistsForTnCType e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.MULTIPLE_ACTIVE_TnCs_EXISTS_FOR_A_TnCTYPE)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  //  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  //  public Problem onHttpRequestMethodNotSupportedException(
  //      HttpRequestMethodNotSupportedException e) {
  //    return Problem.builder().with("details", e.getMessage()).build();
  //  }
  //
  //  @ExceptionHandler(ValueNotOneOfExpectedEnumValues.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem onValueNotOneOfExpectedEnumValues(ValueNotOneOfExpectedEnumValues e) {
  //    log.info(e.toString());
  //    return Problem.builder().with("details", e.getMessage()).build();
  //  }
  //
  //  @ExceptionHandler(SalesforceException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  //  public Problem handleSalesforceException(SalesforceException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.SALESFORCE_INTEGRATION_EXCEPTION)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(CommonAuthServerException.class)
  //  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  //  public Problem handleCommonAuthServerException(CommonAuthServerException e) {
  //    log.info(e.toString());
  //    return Problem.builder()
  //        .withTitle(ErrorConstants.INTERNAL_SERVER_ERROR)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler(TokenCreationException.class)
  //  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  //  public Problem onTokenCreationException(TokenCreationException e) {
  //    return Problem.builder()
  //        .withStatus(Status.SERVICE_UNAVAILABLE)
  //        .withTitle(ErrorConstants.SERVICE_UNAVAILABLE)
  //        .withDetail(e.getMessage())
  //        .build();
  //  }
  //
  //  @ExceptionHandler({GenericClientException.class})
  //  public ResponseEntity onGenericClientException(GenericClientException e) {
  //    log.error("ExceptionControllerAdvice :: onGenericClientException: ", e);
  //    return ResponseEntity.status(Integer.parseInt(e.getErrorDetail().getStatus()))
  //        .body(e.getErrorDetail());
  //  }
  //
  //  @ExceptionHandler({GenericServerException.class})
  //  public ResponseEntity onGenericServerException(GenericServerException e) {
  //    log.error("ExceptionControllerAdvice :: onGenericServerException: ", e);
  //    return ResponseEntity.status(Integer.parseInt(e.getErrorDetail().getStatus()))
  //        .body(e.getErrorDetail());
  //  }
  //
  //  @ExceptionHandler({HandleThirdPartyClientException.class})
  //  public ResponseEntity onHandleThirdPartyClientException(HandleThirdPartyClientException e)
  //      throws JsonProcessingException {
  //    log.error("ExceptionControllerAdvice :: HandleThirdPartyClientException ::", e);
  //    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  //    return ResponseEntity.status(Integer.parseInt(e.getStatusCode()))
  //        .body(objectMapper.readTree(e.getResponseBody()));
  //  }
  //
  //  @ExceptionHandler(UserNotFoundExceptionV2.class)
  //  public ResponseEntity<Object> handleUserNotFoundExceptionV2(UserNotFoundExceptionV2 e) {
  //    log.error("ExceptionControllerAdvice :: UserNotFoundExceptionV2 ::", e);
  //    ErrorDetail errorResponse = new ErrorDetail();
  //    errorResponse.setMessage(e.getMessage());
  //    errorResponse.setTitle(ErrorConstants.BAD_REQUEST);
  //    errorResponse.setStatus(ErrorConstants.ERROR);
  //    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler(ResourceNotFoundExceptionV2.class)
  //  public ResponseEntity<Object> onResourceNotFoundV2(ResourceNotFoundExceptionV2 e) {
  //    log.error("ExceptionControllerAdvice :: ResourceNotFoundExceptionV2 ::", e);
  //    ErrorDetail errorResponse = new ErrorDetail();
  //    errorResponse.setMessage(e.getMessage());
  //    errorResponse.setTitle(ErrorConstants.RESOURCE_NOT_FOUND);
  //    errorResponse.setStatus(ErrorConstants.ERROR);
  //    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  //  }
  //
  //  @ExceptionHandler(BadRequestException.class)
  //  public ResponseEntity<Object> handleBadRequest(BadRequestException e) {
  //    log.error("ExceptionControllerAdvice :: BadRequestException ::", e);
  //    ErrorDetail errorResponse = new ErrorDetail();
  //    errorResponse.setMessage(e.getMessage());
  //    errorResponse.setTitle(ErrorConstants.BAD_REQUEST);
  //    errorResponse.setStatus(ErrorConstants.ERROR);
  //    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler(InvalidRequestExceptionV2.class)
  //  public ResponseEntity<Object> handleInvalidRequestExceptionV2(InvalidRequestExceptionV2 e) {
  //    log.error("ExceptionControllerAdvice :: InvalidRequestExceptionV2 ::", e);
  //    ErrorDetail errorResponse = new ErrorDetail();
  //    errorResponse.setMessage(e.getMessage());
  //    errorResponse.setTitle(ErrorConstants.BAD_REQUEST);
  //    errorResponse.setStatus(ErrorConstants.ERROR);
  //    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler(UserNotAuthorisedV2.class)
  //  public ResponseEntity<Object> handleEmailNotVerifiedException(UserNotAuthorisedV2 e) {
  //    log.error("ExceptionControllerAdvice :: UserNotFoundExceptionV2 ::", e);
  //    var errorResponse = new ErrorDetail();
  //    errorResponse.setMessage(e.getMessage());
  //    errorResponse.setTitle(ErrorConstants.USER_NOT_AUTHORIZED);
  //    errorResponse.setStatus(ErrorConstants.ERROR);
  //    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  //  }
  //
  //  @ExceptionHandler({GenericServerExceptionV2.class})
  //  public ResponseEntity onGenericServerExceptionV2(GenericServerExceptionV2 e) {
  //    log.error("ExceptionControllerAdvice :: onGenericServerExceptionV2: ", e);
  //    return ResponseEntity.status(e.getErrorType().httpStatus)
  //        .body(Utils.getErrorDetailFromErrorType(e.getErrorType()));
  //  }
  //
  //  @ExceptionHandler({GenericClientExceptionV2.class})
  //  public ResponseEntity onGenericClientExceptionV2(GenericClientExceptionV2 e) {
  //    log.error("ExceptionControllerAdvice :: onGenericClientExceptionV2: ", e);
  //    int code =
  //        e.getErrorDetail().getStatusCode() == null
  //            ? Integer.valueOf(e.getErrorDetail().getStatus())
  //            : Integer.valueOf(e.getErrorDetail().getStatusCode());
  //    e.getErrorDetail().setStatus(ErrorConstants.ERROR);
  //    return ResponseEntity.status(code).body(e.getErrorDetail());
  //  }
  //
  //  @ExceptionHandler({
  //    MissingRequestHeaderException.class,
  //    MissingServletRequestParameterException.class,
  //    MethodArgumentTypeMismatchException.class
  //  })
  //  public ResponseEntity handleNullValues(Exception e) {
  //    log.error("ExceptionControllerAdvice :: handleNullValue : ", e);
  //
  //    ErrorDetail errorDetail =
  //        Utils.getErrorDetailFromErrorType(ErrorConstants.ErrorType.BAD_REQUEST);
  //    errorDetail.setMessage(e.getMessage());
  //
  //    return new ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler(CustomException.class)
  //  public ResponseEntity<GenericExceptionResponse> handleCustomException(CustomException e) {
  //    log.error("ExceptionControllerAdvice :: handleCustomException : ", e);
  //
  //    GenericExceptionResponse response = new GenericExceptionResponse();
  //    response.setStatus(ErrorConstants.ERROR);
  //    response.setMessage(e.getMessage());
  //
  //    return new ResponseEntity<>(response, HttpStatus.OK);
  //  }
  //
  //  @ExceptionHandler(Login2faException.class)
  //  public ResponseEntity<Object> handleLogin2faException(Login2faException e) {
  //    return new ResponseEntity<>(e.getData(), HttpStatus.FORBIDDEN);
  //  }
}

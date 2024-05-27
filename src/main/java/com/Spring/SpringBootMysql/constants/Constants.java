package com.Spring.SpringBootMysql.constants;

import com.Spring.SpringBootMysql.exceptions.ValueNotOneOfExpectedEnumValues;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {

  public static final Map<String, String> minKycStatusChangeMap;
  public static final String PAYMENTGATEWAY_ID = "1";
  public static final String X_MERCHANT_ID = "X-Merchant-Id";
  public static final String SMS = "SMS";
  public static final String SUCCESS = "SUCCESS";
  public static final String FAIL = "FAIL";

  static {
    minKycStatusChangeMap = new HashMap<>();
    minKycStatusChangeMap.put(PAYMENTGATEWAY_ID, "minkycstatuschange_paymentgateway");
  }

  public enum MerchantDetailsSourceType {
    // please use PARTNER at the start of string
    // if sourceType belongs to partner.
    MERCHANT_DASHBOARD("MERCHANT_DASHBOARD"),
    ADMIN("ADMIN"),
    PARTNER_DASHBOARD("PARTNER_DASHBOARD"),
    PARTNER_ADD_MERCHANT("PARTNER_ADD_MERCHANT"),
    PARTNER_BULK_MERCHANT("PARTNER_BULK_MERCHANT"),
    PARTNER_BULK_KYC("PARTNER_BULK_KYC"),
    PARTNER_OB_WITHOUT_LOGIN("PARTNER_OB_WITHOUT_LOGIN"),
    PARTNER_OB_WITH_LOGIN("PARTNER_OB_WITH_LOGIN"),
    PARTNER_API("PARTNER_API"),
    PARTNER_SINGLE_KYC("PARTNER_SINGLE_KYC"),
    PARTNER_API_KYC("PARTNER_API_KYC"),
    NOT_A_VALID_SOURCE("NOT_A_VALID_SOURCE"),
    SYSTEM("SYSTEM"),
    SALESFORCE("SALESFORCE"),
    Android("Android"),
    iOS("iOS");

    private final String sourceType;

    MerchantDetailsSourceType(String sourceType) {
      this.sourceType = sourceType;
    }

    @JsonCreator
    public static MerchantDetailsSourceType decode(final String sourceType) {
      if (Objects.equals(sourceType, "") || sourceType == null) {
        return null;
      }
      return Stream.of(MerchantDetailsSourceType.values())
          .filter(targetEnum -> targetEnum.sourceType.equals(sourceType))
          .findFirst()
          .orElse(NOT_A_VALID_SOURCE);
    }

    public static void allowOnlyValidSourceTypeRequest(MerchantDetailsSourceType sourceType) {
      if (sourceType != null && sourceType.equals(NOT_A_VALID_SOURCE)) {
        throw new ValueNotOneOfExpectedEnumValues(
            "Source type is not one of acceptable values:["
                + Arrays.stream(Constants.MerchantDetailsSourceType.values())
                    .map(Constants.MerchantDetailsSourceType::getSourceType)
                    .collect(Collectors.joining(", "))
                + "]");
      }
    }

    @JsonValue
    public String getSourceType() {
      return sourceType;
    }
  }

  public static final class Status {
    public static final String ACTIVE = "ACTIVE";
    public static final String IN_ACTIVE = "IN_ACTIVE";
  }

  public static final class HttpConstant {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_BEARER_HEADER_VALUE_PREFIX = "Bearer ";

    public static final String AUTHORIZATION_BASIC_HEADER_VALUE_PREFIX = "Basic ";
    public static final int AUTHORIZATION_BEARER_PREFIX_LENGTH = 7;
  }
}

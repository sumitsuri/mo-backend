package com.Spring.SpringBootMysql.utils;

import com.Spring.SpringBootMysql.constants.ErrorConstants;
import com.Spring.SpringBootMysql.domains.common.ErrorDetail;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class Utils {
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{1,50}$", Pattern.CASE_INSENSITIVE);
  private static final String B64T_STRING =
      "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final char[] B64T_ARRAY = B64T_STRING.toCharArray();
  final String AES_SECRET_KEY = "2B7E151628AED2A6ABF7158809CF4F3C";
  private final ObjectMapper objectMapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  char[] pwdChars =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&+.=".toCharArray();
  Random rnd = new Random();

  private static void b64from24bit(
      final byte b2, final byte b1, final byte b0, final int outLen, final StringBuilder buffer) {
    int w = ((b2 << 16) & 0x00ffffff) | ((b1 << 8) & 0x00ffff) | (b0 & 0xff);
    int n = outLen;
    while (n-- > 0) {
      buffer.append(B64T_ARRAY[w & 0x3f]);
      w >>= 6;
    }
  }

  public static boolean validate(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }

  public static String getDateYYYYMMDD(LocalDateTime localDateTime) {
    if (Objects.isNull(localDateTime)) {
      return null;
    }
    return localDateTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD));
  }

  public static Boolean isValidPhoneNo(String countryCode, String phoneNo) {
    String phoneRegex = "\\d{10,13}$";
    if (Objects.equals(countryCode, "+91")) {
      phoneRegex = "\\d{10}$";
    }
    Pattern pattern = Pattern.compile(phoneRegex);
    return pattern.matcher(phoneNo).matches();
  }

  public static <T> T convertLinkedHashMapToObj(LinkedHashMap<String, Object> x, Class<T> y) {
    try {
      ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
      objectMapper.findAndRegisterModules(); // register JavaTimeModule to support LocalDateTime

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

      SimpleModule module = new SimpleModule();
      module.addDeserializer(
          LocalDateTime.class,
          new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
              String dateString = jsonParser.getText();
              return LocalDateTime.parse(dateString, formatter);
            }
          });
      objectMapper.registerModule(module);
      byte[] bytes = objectMapper.writeValueAsBytes(x);
      return objectMapper.readValue(bytes, y);
    } catch (IOException ex) {
      log.error("Error Parsing linked hash map", ex);
    }
    return null;
  }

  public static Integer getDaysPassedTillNow(LocalDateTime pastDateTime) {
    LocalDate pastDate = pastDateTime.toLocalDate();
    LocalDate todayDate = LocalDate.now();
    return (int) ChronoUnit.DAYS.between(pastDate, todayDate);
  }

  public static int generateRandomOtp(int otpSize) {
    // assuming otp size won't overflow Int type
    long min = (long) Math.pow(10, otpSize - 1);
    long max = (long) Math.pow(10, otpSize) - 1;
    SecureRandom random = new SecureRandom();
    long randomNumber = Math.abs(random.nextLong());

    randomNumber = (randomNumber % (max - min + 1)) + min;
    return (int) randomNumber;
  }

  //  public String md5Crypt(final byte[] keyBytes, final String salt) {
  //    final int keyLen = keyBytes.length;
  //    String prefix = "$1$";
  //    String saltString;
  //    if (salt == null) {
  //      saltString = "";
  //    } else {
  //      saltString = salt.substring(3, salt.length() - 1);
  //    }
  //    final byte[] saltBytes = saltString.getBytes(Charsets.UTF_8);
  //
  //    final MessageDigest ctx = DigestUtils.getMd5Digest();
  //
  //    ctx.update(keyBytes);
  //
  //    ctx.update(prefix.getBytes(Charsets.UTF_8));
  //
  //    ctx.update(saltBytes);
  //
  //    MessageDigest ctx1 = DigestUtils.getMd5Digest();
  //    ctx1.update(keyBytes);
  //    ctx1.update(saltBytes);
  //    ctx1.update(keyBytes);
  //    byte[] finalb = ctx1.digest();
  //    int ii = keyLen;
  //    while (ii > 0) {
  //      ctx.update(finalb, 0, ii > 16 ? 16 : ii);
  //      ii -= 16;
  //    }
  //
  //    Arrays.fill(finalb, (byte) 0);
  //
  //    ii = keyLen;
  //    final int j = 0;
  //    while (ii > 0) {
  //      if ((ii & 1) == 1) {
  //        ctx.update(finalb[j]);
  //      } else {
  //        ctx.update(keyBytes[j]);
  //      }
  //      ii >>= 1;
  //    }
  //
  //    final StringBuilder passwd = new StringBuilder(prefix + saltString + "$");
  //    finalb = ctx.digest();
  //
  //    for (int i = 0; i < 1000; i++) {
  //      ctx1 = DigestUtils.
  //      if ((i & 1) != 0) {
  //        ctx1.update(keyBytes);
  //      } else {
  //        ctx1.update(finalb, 0, 16);
  //      }
  //
  //      if (i % 3 != 0) {
  //        ctx1.update(saltBytes);
  //      }
  //
  //      if (i % 7 != 0) {
  //        ctx1.update(keyBytes);
  //      }
  //
  //      if ((i & 1) != 0) {
  //        ctx1.update(finalb, 0, 16);
  //      } else {
  //        ctx1.update(keyBytes);
  //      }
  //      finalb = ctx1.digest();
  //    }
  //
  //    b64from24bit(finalb[0], finalb[6], finalb[12], 4, passwd);
  //    b64from24bit(finalb[1], finalb[7], finalb[13], 4, passwd);
  //    b64from24bit(finalb[2], finalb[8], finalb[14], 4, passwd);
  //    b64from24bit(finalb[3], finalb[9], finalb[15], 4, passwd);
  //    b64from24bit(finalb[4], finalb[10], finalb[5], 4, passwd);
  //    b64from24bit((byte) 0, (byte) 0, finalb[11], 2, passwd);
  //
  //    ctx.reset();
  //    ctx1.reset();
  //    Arrays.fill(keyBytes, (byte) 0);
  //    Arrays.fill(saltBytes, (byte) 0);
  //    Arrays.fill(finalb, (byte) 0);
  //
  //    return passwd.toString();
  //  }

  //  public String mutateToken(String token) {
  //    String[] parts = token.split("\\.");
  //    String randomString;
  //
  //    for (int i = 0; i < parts.length; i++) {
  //
  //      randomString = generateRandomString(2);
  //      StringBuffer buffer = new StringBuffer(parts[i]);
  //      parts[i] = randomString.concat(buffer.reverse().toString());
  //    }
  //    return StringUtils.join(parts, ".");
  //  }

  //  public String unMutateToken(String token) {
  //    String[] parts = token.split("\\.");
  //
  //    for (int i = 0; i < parts.length; i++) {
  //      StringBuffer buffer = new StringBuffer(parts[i].substring(2));
  //      parts[i] = buffer.reverse().toString();
  //    }
  //    return StringUtils.join(parts, ".");
  //  }

  public static String maskPhoneNumber(String countryCode, String phoneNumber) {
    String maskPhoneNumber = null;
    if (phoneNumber != null && phoneNumber.length() > 4) {
      String mask = "xxxxxx";
      String last4Digit = phoneNumber.substring(phoneNumber.length() - 4);
      ;
      if (countryCode != null) {
        maskPhoneNumber = countryCode + mask + last4Digit;
      } else {
        maskPhoneNumber = mask + last4Digit;
      }
    }
    return maskPhoneNumber;
  }

  //  public Claims extractEmailVerificationToken(String token) throws Exception {
  //    Claims data;
  //    String jwt;
  //    try {
  //      jwt = unMutateToken(token);
  //      data = jwtUtil.getAllClaimsFromToken(jwt);
  //    } catch (StringIndexOutOfBoundsException ex) {
  //      throw new JwtTokenInvalidException("Invalid JWT");
  //    } catch (ExpiredJwtException e) {
  //      throw new JwtTokenExpiredException("JWT expired");
  //    } catch (Exception e) {
  //      throw new JwtTokenInvalidException("Error extracting JWT");
  //    }
  //    return data;
  //  }

  public static ErrorDetail getErrorDetailFromErrorType(ErrorConstants.ErrorType errorType) {
    ErrorDetail errorDetail = new ErrorDetail();
    errorDetail.setTitle(errorType.errorTitle);
    errorDetail.setMessage(errorType.errorMessage);
    errorDetail.setStatus(errorType.errorStatus);
    errorDetail.setStatusCode(errorType.httpStatus + "");

    return errorDetail;
  }

  public static String extractClientIP(String ipAddresses) {
    if (ipAddresses == null) return "X-clientIP";

    String[] ips = ipAddresses.split(",");

    // This is the default value in case the header(X-Forwarded-For) from Kong does not have an
    // actual IP
    if (ips.length == 0) return "X-clientIP";
    // First Ip of X-Forwarded-For is client IP.
    return ips[0];
  }

  public String encryptPassword(String password) {
    String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
    return encryptedPassword;
  }

  public Boolean isValidPassword(String password, String storedPassword) {
    return password != null
        && !password.isBlank()
        && storedPassword != null
        && !storedPassword.isBlank()
        && BCrypt.checkpw(password, storedPassword);
  }

  //  public List<Long> getHigherLevelPermissionsFromPermissionCode(String permissionCode) {
  //    List<Long> permissionCodes = new ArrayList<>();
  //    if (permissionCode.length() == 5) {
  //      permissionCodes.add(Long.valueOf(permissionCode));
  //      permissionCode = permissionCode.substring(0, 4);
  //    }
  //    if (permissionCode.length() == 4) {
  //      permissionCodes.add(Long.valueOf(permissionCode));
  //      permissionCode = permissionCode.substring(0, 3);
  //    }
  //    if (permissionCode.length() == 3) {
  //      permissionCode = permissionCode.substring(0, 1).concat("00");
  //      permissionCodes.add(Long.valueOf(permissionCode));
  //    } else {
  //      throw new InvalidAliasPermissionCodeException("Invalid Alias PermCode: " +
  // permissionCode);
  //    }
  //    return permissionCodes;
  //  }

  //  public String generateAlphaNumericSpecialCharString(int len) {
  //    String values = CAPITAL_CHARS + SMALL_CHARS + NUMBERS + SYMBOLS;
  //    Random rndm_method = new Random();
  //    String password = "";
  //    for (int i = 0; i < len; i++) {
  //      password += values.charAt(rndm_method.nextInt(values.length()));
  //    }
  //    return password;
  //  }

  //  public String getHashString(String input, String algorithm) {
  //    try {
  //      MessageDigest md = MessageDigest.getInstance(algorithm);
  //      byte[] messageDigest = md.digest(input.getBytes());
  //      BigInteger no = new BigInteger(1, messageDigest);
  //      StringBuilder hashtext = new StringBuilder(no.toString(16));
  //      while (hashtext.length() < 32) {
  //        hashtext.insert(0, "0");
  //      }
  //      return hashtext.toString();
  //    } catch (NoSuchAlgorithmException e) {
  //      throw new HashingException(e.getMessage());
  //    }
  //  }

  //  public static String replaceAllRegexPattern(String input, Pattern regex, String replace) {
  //    StringBuilder sb = new StringBuilder();
  //    Matcher matcher = regex.matcher(input);
  //    while (matcher.find()) {
  //      matcher.appendReplacement(sb, replace);
  //    }
  //    matcher.appendTail(sb);
  //    return sb.toString();
  //  }
  //
  //  public <T> T mapObject(String s, Class<T> t) {
  //    try {
  //      return objectMapper.readValue(s, t);
  //    } catch (JsonProcessingException e) {
  //      log.error("Error Parsing Json", e);
  //      throw new UnparseableJsonException("Unable to map object", e);
  //    }
  //  }

  public Map<String, Object> entityToMap(Object entity) {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    String jsonStringEntity = "";

    Map<String, Object> result = new HashMap<>();

    try {
      jsonStringEntity = objectMapper.writeValueAsString(entity);
      result = objectMapper.readValue(jsonStringEntity, Map.class);
    } catch (JsonProcessingException e) {
      log.error("LOG_ERROR :: UTILS :: ENTITY_MAPPING_FAILED :: " + entity);
      e.printStackTrace();
    }

    return result;
  }

  public int timeDiff(LocalDateTime time) {
    LocalDateTime currentTime = LocalDateTime.now();
    if (time == null) {
      time = LocalDateTime.now().minusDays(1);
    }
    LocalDateTime tempDateTime = LocalDateTime.from(time);

    Long days = tempDateTime.until(currentTime, ChronoUnit.DAYS);
    Long hours = tempDateTime.until(currentTime, ChronoUnit.HOURS);
    Long minutes = tempDateTime.until(currentTime, ChronoUnit.MINUTES);

    return (int) (days * 24 * 60 + hours * 60 + minutes);
  }

  //  public Map<String, Object> getJwtDetailFromToken(
  //      String token, Boolean isOnboardingSpecificToken) {
  //    String jwtToken = unMutateToken(token);
  //    if (isOnboardingSpecificToken) {
  //      Map<String, Object> claims = jwtUtil.getAllClaimsFromOnboardingToken(jwtToken);
  //      return claims;
  //    }
  //    Map<String, Object> claims = jwtUtil.getAllClaimsFromToken(jwtToken);
  //    return claims;
  //  }

  public long timeElapsedSeconds(LocalDateTime time) {
    return time.until(LocalDateTime.now(), ChronoUnit.SECONDS);
  }

  public String mapToJsonString(Map<String, Object> inputMap) {
    ObjectMapper mapperObj = new ObjectMapper().registerModule(new JavaTimeModule());
    String jsonResp = "{}";
    try {
      jsonResp = mapperObj.writeValueAsString(inputMap);
    } catch (IOException e) {
      log.error("LOG_ERROR :: UTILS :: MAP_TO_JSON_STRING :: ");
      e.printStackTrace();
    }
    return jsonResp;
  }

  public String generateRandomString(int length) {
    String string = "ABCDFGHJKLMNPQRSTUVWXYZ23456789bcdefghjklmnpqrstuvwxyz";
    List<String> letters = Arrays.asList(string.split(""));
    Collections.shuffle(letters);
    StringBuilder builder = new StringBuilder();
    for (String letter : letters) {
      builder.append(letter);
    }
    return builder.substring(0, length);
  }

  public String encryptWithAES(String input) throws Exception {
    // Create a SecretKeySpec from the secret key
    SecretKeySpec secretKeySpec =
        new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

    // Create an AES cipher and initialize it with the secret key and IV
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[16]));

    // Encrypt the input string
    byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

    // Encode the encrypted bytes to Base64 for safe storage/transmission
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public String decryptWithAES(String encryptedInput) throws Exception {
    // Decode the Base64-encoded encrypted string
    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);

    // Create a SecretKeySpec from the secret key
    SecretKeySpec secretKeySpec =
        new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

    // Create an AES cipher and initialize it with the secret key
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[16]));

    // Decrypt the encrypted bytes
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

    // Convert the decrypted bytes back to a string
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }

  //  public static String getSourceTypeHeaderFromServletRequestAttributes() {
  //    var servletRequestAttributes =
  //        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
  //    if (servletRequestAttributes == null) return null;
  //
  //    Object o = servletRequestAttributes.getAttribute(SOURCE_TYPE,
  // RequestAttributes.SCOPE_REQUEST);
  //    return o == null ? null : o.toString();
  //  }

  public String[] decodeOnboardingLinkWithLoginToken(String token) {
    return token.split("\\|");
  }

  public String randomUserPwd() {
    int len = 9 + (int) (Math.random() * ((19 - 9) + 1));
    String rndPwd = "";
    for (int i = 0; i < len; i++) {
      rndPwd = rndPwd + pwdChars[rnd.nextInt(pwdChars.length)];
    }
    return rndPwd;
  }

  //  public String mutateTokenWithCustomSeparator(String token, String separator) {
  //    String[] parts = token.split(Pattern.quote(separator));
  //
  //    for (int i = 0; i < parts.length; i++) {
  //      String randomStringForPrefix = generateRandomString(2);
  //      String randomStringForSuffix = generateRandomString(3);
  //      StringBuilder buffer = new StringBuilder(parts[i]);
  //      parts[i] =
  //
  // randomStringForPrefix.concat(buffer.reverse().toString()).concat(randomStringForSuffix);
  //    }
  //    return StringUtils.join(parts, separator);
  //  }

  //  public String unMutateTokenWithCustomSeparator(String token, String separator) {
  //    String[] parts = token.split(Pattern.quote(separator));
  //
  //    for (int i = 0; i < parts.length; i++) {
  //      StringBuilder buffer = new StringBuilder(parts[i].substring(2, parts[i].length() - 3));
  //      parts[i] = buffer.reverse().toString();
  //    }
  //    return StringUtils.join(parts, separator);
  //  }

  public String convertObjectToString(Object object) throws Exception {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    return mapper.writeValueAsString(object);
  }

  public String getSecureHash(String input, String salt, String algorithm)
      throws NoSuchAlgorithmException {
    if (algorithm == null) algorithm = "SHA-256";
    if (salt == null) salt = "";
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(salt.getBytes());
      byte[] bytes = md.digest(input.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte aByte : bytes) {
        sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException exception) {
      log.error("Invalid algorithm", exception);
      throw exception;
    }
  }
}

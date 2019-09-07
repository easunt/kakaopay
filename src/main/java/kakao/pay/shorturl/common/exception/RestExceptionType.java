package kakao.pay.shorturl.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RestExceptionType {

    NOT_VALID_SHORTEN_URL("1100", HttpStatus.BAD_REQUEST, "Requested short url is not valid."),
    URL_IS_NOT_VALID("1101", HttpStatus.BAD_REQUEST, "Requested url is not valid."),
    URL_NOT_EXIST("1102", HttpStatus.BAD_REQUEST, "Requested short url is not exist."),

    CAN_NOT_ENCODE_STRING("1200", HttpStatus.INTERNAL_SERVER_ERROR, "Can not encode string no more."),

    INTERNAL_SERVER_ERROR("9999", HttpStatus.INTERNAL_SERVER_ERROR, "");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    RestExceptionType(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

package kakao.pay.shorturl.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RestException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3713800883410031528L;

    private Integer status;
    private String code;
    private String message;
    private String view;

    public RestException(RestExceptionType restExceptionType) {
        super(restExceptionType.name());
        this.status = restExceptionType.getHttpStatus().value();
        this.code = restExceptionType.getCode();
        this.message = restExceptionType.getMessage();
        this.view = Integer.toString(restExceptionType.getHttpStatus().value());
    }
}

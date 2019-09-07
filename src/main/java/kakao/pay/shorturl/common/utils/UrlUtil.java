package kakao.pay.shorturl.common.utils;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.common.exception.RestExceptionType;

import java.net.URL;
import java.net.URLConnection;

public class UrlUtil {

    public static void isValidUrl(String url) {
        try {
            URL urlObject = new URL(url);
            URLConnection conn = urlObject.openConnection();
            conn.connect();
        } catch (Exception e) {
            throw new RestException(RestExceptionType.URL_IS_NOT_VALID);
        }
    }
}


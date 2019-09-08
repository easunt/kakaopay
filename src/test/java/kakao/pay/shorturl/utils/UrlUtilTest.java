package kakao.pay.shorturl.utils;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.common.utils.UrlUtil;
import org.junit.Test;

public class UrlUtilTest {
    @Test
    public void URL검증_OK() {
        UrlUtil.isValidUrl("https://www.kakaopay.com");
        UrlUtil.isValidUrl("https://www.google.com");
        UrlUtil.isValidUrl("https://www.github.com");
    }

    @Test(expected = RestException.class)
    public void URL검증_FORMAT_ERROR() {
        UrlUtil.isValidUrl("tcp://192.168.0.1");
    }

    @Test(expected = RestException.class)
    public void URL검증_NOT_ACCESS_ERROR() {
        UrlUtil.isValidUrl("http://abc.fff.ddd");
    }
}

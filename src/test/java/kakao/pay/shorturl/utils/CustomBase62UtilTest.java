package kakao.pay.shorturl.utils;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.common.utils.CustomBase62Util;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomBase62UtilTest {

    @Test
    public void BASE62인코딩_OK() {
        String result = CustomBase62Util.encodeLongtoString(1);
        assertThat(result).isEqualTo("Y6666");

        result = CustomBase62Util.encodeLongtoString(1111);
        assertThat(result).isEqualTo("Y66CN");

        result = CustomBase62Util.encodeLongtoString(218340090808550L);
        assertThat(result).isEqualTo("UUUUUUU5");
    }

    @Test(expected = RestException.class)
    public void BASE62인코딩_ERROR() {
        CustomBase62Util.encodeLongtoString(218340090808561L);  //62^8 - 62^4
    }

    @Test
    public void BASE62디코딩_OK() {
        long result = CustomBase62Util.decodeStringtoLong("Y6666");
        assertThat(result).isEqualTo(1);

        result = CustomBase62Util.decodeStringtoLong("Y66CN");
        assertThat(result).isEqualTo(1111);

        result = CustomBase62Util.decodeStringtoLong("UUUUUUU5");
        assertThat(result).isEqualTo(218340090808550L);
    }

    @Test(expected = RestException.class)
    public void BASE62디코딩_문자열길이_ERROR() {
        CustomBase62Util.decodeStringtoLong("AAAAAAAAAAAAAZ");
    }

    @Test(expected = RestException.class)
    public void BASE62디코딩_인코딩문자_ERROR() {
        CustomBase62Util.decodeStringtoLong("ADF=AE+");
    }

    @Test(expected = RestException.class)
    public void BASE62디코딩_공백_ERROR() {
        CustomBase62Util.decodeStringtoLong("AD8 FAE");
    }
}

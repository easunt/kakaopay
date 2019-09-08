package kakao.pay.shorturl.common.utils;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.common.exception.RestExceptionType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomBase62Util {
    private static final String BASE62 = "6Y8E19bHPwqIifk2cCBDVtlRhjOuxzTgnMXoQA743FaSJymdeZW5Kp0sNGLvrU";
    private static final long BASE_NUMBER = 14776335;
    private static final int MAX_LENGTH_LIMIT = 8;
    private static final int MIN_LENGTH_LIMIT = 5;

    public static String encodeLongtoString(long input) {
        char[] base62Char = BASE62.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        input = input + BASE_NUMBER;

        for (; input > 0; input /= 62) {
            stringBuilder.append(base62Char[(int) (input % 62L)]);
        }

        if (stringBuilder.length() > MAX_LENGTH_LIMIT) {
            throw new RestException(RestExceptionType.CAN_NOT_ENCODE_STRING);
        }

        return stringBuilder.reverse().toString();
    }

    public static long decodeStringtoLong(String input) {
        isCustomBase64(input);
        long output = 0;
        for (int i = 0; i < input.length(); i++) {
            output = output * 62L + (long) BASE62.indexOf(input.charAt(i));
        }

        return output - BASE_NUMBER;
    }

    public static void isCustomBase64(String input) {
        String regex = "^[a-zA-Z0-9]{" + MIN_LENGTH_LIMIT + "," + MAX_LENGTH_LIMIT + "}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new RestException(RestExceptionType.NOT_VALID_SHORTEN_URL);
        }

    }
}

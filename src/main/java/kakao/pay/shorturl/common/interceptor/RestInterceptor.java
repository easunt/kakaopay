package kakao.pay.shorturl.common.interceptor;

import kakao.pay.shorturl.common.utils.CustomBase62Util;
import kakao.pay.shorturl.common.utils.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class RestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getMethod().equals("GET")) {
            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String shortenUrl = pathVariables.get("shortenUrl");

            if (!StringUtils.isBlank(shortenUrl)) {
                CustomBase62Util.isCustomBase64(shortenUrl);
            }
        } else if (request.getMethod().equals("POST")) {
            UrlUtil.isValidUrl(request.getParameter("url"));
        }

        return true;
    }
}

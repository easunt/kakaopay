package kakao.pay.shorturl.config;

import kakao.pay.shorturl.common.interceptor.RestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RestInterceptor restInterceptor;

    public WebMvcConfig(RestInterceptor restInterceptor) {
        this.restInterceptor = restInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restInterceptor)
                .addPathPatterns("/**");
    }
}

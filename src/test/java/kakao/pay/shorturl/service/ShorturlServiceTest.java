package kakao.pay.shorturl.service;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.config.SystemConfig;
import kakao.pay.shorturl.entity.ShorturlEntity;
import kakao.pay.shorturl.repository.ShorturlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ShorturlService.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@EnableConfigurationProperties(value = {SystemConfig.class})
public class ShorturlServiceTest {
    @Autowired
    private ShorturlService shorturlService;

    @MockBean
    private ShorturlRepository shorturlRepository;

    private final String ORIGINAL_URL = "https://www.kakaopay.com";
    private final String SHORT_URL = "http://localhost/Y6666";

    @Test
    public void 단축URL생성_OK() {

        when(shorturlRepository.findByUrl(any(java.lang.String.class))).thenReturn(Optional.of(getShortUrlEntity()));

        String result = shorturlService.createShorturl(ORIGINAL_URL);
        assertThat(result.length()).isBetween(22, 25);
        assertThat(result).isEqualTo(SHORT_URL);
    }

    @Test(expected = RestException.class)
    public void 단축URL생성_인코딩_ERROR() {

        when(shorturlRepository.findByUrl(any(java.lang.String.class))).thenReturn(Optional.of(getOverflowUrlEntity()));

        shorturlService.createShorturl(ORIGINAL_URL);
    }

    @Test
    public void 단축URL가져오기_OK(){

        when(shorturlRepository.findById(any(Long.class))).thenReturn(Optional.of(getShortUrlEntity()));

        String result = shorturlService.getOriginalUrl("Y6666");
        assertThat(result).isEqualTo(ORIGINAL_URL);
    }

    @Test(expected = RestException.class)
    public void 단축URL없음_ERROR() {

        when(shorturlRepository.findById(any(Long.class))).thenThrow(RestException.class);

        shorturlService.getOriginalUrl("YZASD");
    }

    @Test(expected = RestException.class)
    public void 단축URL디코딩_ERROR() {

        shorturlService.getOriginalUrl("YZ+AS=D");
    }

    public ShorturlEntity getShortUrlEntity() {
        ShorturlEntity shorturlEntity = new ShorturlEntity();
        shorturlEntity.setId(1L);
        shorturlEntity.setUrl(ORIGINAL_URL);
        return shorturlEntity;
    }

    public ShorturlEntity getOverflowUrlEntity() {
        ShorturlEntity shorturlEntity = new ShorturlEntity();
        shorturlEntity.setId(218340090808561L);
        shorturlEntity.setUrl(ORIGINAL_URL);
        return shorturlEntity;
    }

}

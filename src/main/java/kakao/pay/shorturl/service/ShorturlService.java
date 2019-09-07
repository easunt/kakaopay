package kakao.pay.shorturl.service;

import kakao.pay.shorturl.common.exception.RestException;
import kakao.pay.shorturl.common.exception.RestExceptionType;
import kakao.pay.shorturl.common.utils.CustomBase62Util;
import kakao.pay.shorturl.config.SystemConfig;
import kakao.pay.shorturl.entity.ShorturlEntity;
import kakao.pay.shorturl.repository.ShorturlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShorturlService {
    private final SystemConfig systemConfig;
    private final ShorturlRepository shorturlRepository;

    public String createShorturl(String url) {
        ShorturlEntity shorturlEntity = shorturlRepository.findByUrl(url)
                .orElseGet(() -> shorturlRepository.save(ShorturlEntity.createInstance(url)));

        String encodedId = CustomBase62Util.encodeLongtoString(shorturlEntity.getId());
        return systemConfig.getDomain() + encodedId;
    }

    public String getOriginalUrl(String shrotenUrl) {
        Long decodedId = CustomBase62Util.decodeStringtoLong(shrotenUrl);
        ShorturlEntity shorturlEntity = shorturlRepository.findById(decodedId)
                .orElseThrow(() -> new RestException(RestExceptionType.URL_NOT_EXIST));

        return shorturlEntity.getUrl();
    }
}

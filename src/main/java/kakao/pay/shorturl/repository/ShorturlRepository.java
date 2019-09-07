package kakao.pay.shorturl.repository;

import kakao.pay.shorturl.entity.ShorturlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShorturlRepository extends JpaRepository<ShorturlEntity, Long > {
    Optional<ShorturlEntity> findByUrl(String url);
}


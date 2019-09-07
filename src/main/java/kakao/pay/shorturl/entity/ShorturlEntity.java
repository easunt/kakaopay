package kakao.pay.shorturl.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "short_url")
@NoArgsConstructor
public class ShorturlEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    public static ShorturlEntity createInstance(String url) {
        ShorturlEntity shorturlEntity = new ShorturlEntity();
        shorturlEntity.setUrl(url);
        return shorturlEntity;
    }
}


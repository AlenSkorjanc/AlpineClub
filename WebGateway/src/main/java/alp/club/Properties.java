package alp.club;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class Properties {

    private String usersUrl;

    private String eventsUrl;

    private String articlesUrl;

}

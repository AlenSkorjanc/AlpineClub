package alp.club.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class Properties {

    private String apiUrl;

    private String appVersion;

    private String releaseStage;

    private String bugsnagApiKey;

}

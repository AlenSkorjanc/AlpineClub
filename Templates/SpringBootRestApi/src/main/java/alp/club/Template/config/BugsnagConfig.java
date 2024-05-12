package alp.club.Template.config;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfig {

    @Value("${bugsnagApiKey}")
    private String bugsnagApiKey;

    @Value("${appVersion}")
    private String appVersion;

    @Value("${releaseStage}")
    private String releaseStage;

    @Bean
    public Bugsnag bugsnag() {
        Bugsnag bugsnag = new Bugsnag(bugsnagApiKey);
        bugsnag.setAppVersion(appVersion);
        bugsnag.setReleaseStage(releaseStage);
        return bugsnag;
    }
}
package alp.club.Users.config;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfig {

    @Value("${releaseStage}")
    private String releaseStage;

    @Bean
    public Bugsnag bugsnag() {
        Bugsnag bugsnag = new Bugsnag("36622396d1e003e195a9c6b1fd3d67d0");
        bugsnag.setAppVersion("1.0");
        bugsnag.setReleaseStage(releaseStage);
        return bugsnag;
    }
}
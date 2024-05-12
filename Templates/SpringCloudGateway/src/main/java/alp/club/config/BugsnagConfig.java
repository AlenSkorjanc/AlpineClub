package alp.club.config;

import com.bugsnag.Bugsnag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BugsnagConfig {

    @Autowired
    private Properties properties;

    @Bean
    public Bugsnag bugsnag() {
        Bugsnag bugsnag = new Bugsnag(properties.getBugsnagApiKey());
        bugsnag.setAppVersion(properties.getAppVersion());
        bugsnag.setReleaseStage(properties.getReleaseStage());
        return bugsnag;
    }
}
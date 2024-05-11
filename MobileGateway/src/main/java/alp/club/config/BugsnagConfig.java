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
        Bugsnag bugsnag = new Bugsnag("f18e0305730d5c7fcf329a8a4b071b4a");
        bugsnag.setAppVersion("1.0");
        bugsnag.setReleaseStage(properties.getReleaseStage());
        return bugsnag;
    }
}
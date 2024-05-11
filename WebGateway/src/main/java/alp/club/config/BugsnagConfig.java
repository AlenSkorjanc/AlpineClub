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
        Bugsnag bugsnag = new Bugsnag("b804c0bc006c3fc81d3d17afd4885b35");
        bugsnag.setAppVersion("1.0");
        bugsnag.setReleaseStage(properties.getReleaseStage());
        return bugsnag;
    }
}
package alp.club.config;

import com.bugsnag.Bugsnag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class BugsnagService {

    private final Bugsnag bugsnag;

    @Inject
    public BugsnagService(@ConfigProperty(name = "releaseStage") String releaseStage, @ConfigProperty(name = "appVersion") String appVersion, @ConfigProperty(name = "bugsnagApiKey") String bugsnagApiKey) {
        this.bugsnag = new Bugsnag(bugsnagApiKey);
        this.bugsnag.setAppVersion(appVersion);
        this.bugsnag.setReleaseStage(releaseStage);
    }

    public boolean notify(Throwable throwable) {
        return bugsnag.notify(throwable);
    }
}
package alp.club.config;

import com.bugsnag.Bugsnag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class BugsnagService {

    private final Bugsnag bugsnag;

    @Inject
    public BugsnagService(@ConfigProperty(name = "releaseStage") String releaseStage) {
        this.bugsnag = new Bugsnag("cceced9cd2af2a3e135415870e5b6aec");
        this.bugsnag.setReleaseStage(releaseStage);
        this.bugsnag.setAppVersion("1.0");
    }

    public boolean notify(Throwable throwable) {
        return bugsnag.notify(throwable);
    }
}
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
        this.bugsnag = new Bugsnag("51bdaa5c578542e56da0f3adf6793933");
        this.bugsnag.setReleaseStage(releaseStage);
        this.bugsnag.setAppVersion("1.0");
    }

    public boolean notify(Throwable throwable) {
        return bugsnag.notify(throwable);
    }
}
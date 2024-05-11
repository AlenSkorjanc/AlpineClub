package alp.club.config;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Inject
    private BugsnagService bugsnag;

    @Override
    public Response toResponse(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);

        bugsnag.notify(throwable);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An error occurred: " + throwable.getMessage())
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}
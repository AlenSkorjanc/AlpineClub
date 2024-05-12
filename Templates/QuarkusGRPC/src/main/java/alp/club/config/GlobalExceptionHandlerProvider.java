package alp.club.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.ExceptionHandler;
import io.quarkus.grpc.ExceptionHandlerProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GlobalExceptionHandlerProvider implements ExceptionHandlerProvider {

    @Inject
    BugsnagService bugsnagService;

    @Override
    public <ReqT, RespT> ExceptionHandler<ReqT, RespT> createHandler(ServerCall.Listener<ReqT> listener, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
        return new GlobalExceptionHandler<>(listener, serverCall, metadata, bugsnagService);
    }

    @Override
    public Throwable transform(Throwable t) {
        return ExceptionHandlerProvider.toStatusException(t, true);
    }

    private static class GlobalExceptionHandler<A, B> extends ExceptionHandler<A, B> {

        private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        private final BugsnagService bugsnagService;

        public GlobalExceptionHandler(ServerCall.Listener<A> listener, ServerCall<A, B> call, Metadata metadata, BugsnagService bugsnagService) {
            super(listener, call, metadata);
            this.bugsnagService = bugsnagService;
        }

        @Override
        protected void handleException(Throwable t, ServerCall<A, B> call, Metadata metadata) {
            logger.error(t.getMessage(), t);
            bugsnagService.notify(t);

            StatusRuntimeException sre = (StatusRuntimeException) ExceptionHandlerProvider.toStatusException(t, true);
            Metadata trailers = sre.getTrailers() != null ? sre.getTrailers() : metadata;
            call.close(sre.getStatus(), trailers);
        }
    }
}
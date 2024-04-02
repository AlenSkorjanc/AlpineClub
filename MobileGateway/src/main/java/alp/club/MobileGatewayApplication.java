package alp.club;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorDsl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.HttpMessageReader;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class MobileGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileGatewayApplication.class, args);
    }

    private final String GATEWAY_HEADER_KEY = "Gateway";
    private final String GATEWAY_HEADER_VALUE = "Mobile";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, Properties properties) {
        return builder.routes()
                .route(r -> r.path("/users/**")
                        .filters(f -> {
                            return f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                    .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE);
                        })
                        .uri(properties.getUsersUrl())
                )
                .route(r -> r.path("/events/**")
                        .filters(f -> {
                            return f.stripPrefix(1)
                                    .addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                    .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE);
                        })
                        .uri(properties.getEventsUrl())
                )
                .route(r -> r.path("/articles/**")
                        .filters(f -> {
                            return f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                    .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE);
                        })
                        .uri(properties.getArticlesUrl())
                )
                .build();
    }

}

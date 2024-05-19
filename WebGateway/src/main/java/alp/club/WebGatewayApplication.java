package alp.club;

import alp.club.config.Properties;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class WebGatewayApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebGatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class, args);
    }

    private final String GATEWAY_HEADER_KEY = "Gateway";
    private final String GATEWAY_HEADER_VALUE = "Web";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, Properties properties) {
        logger.info("Release stage: {}", properties.getReleaseStage());
        logger.info("Events URL: {}", properties.getEventsUrl());

        return builder.routes()
                .route(r -> r.path("/users/**")
                        .filters(f -> f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE))
                        .uri(properties.getUsersUrl())
                )
                .route(r -> r.path("/articles/**")
                        .filters(f -> f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE))
                        .uri(properties.getArticlesUrl())
                )
                .route(r -> r.path("/**")
                        .filters(f -> f
                                .addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE))
                        .uri(properties.getEventsUrl())
                )
                .build();
    }
}

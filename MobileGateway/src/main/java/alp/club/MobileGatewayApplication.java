package alp.club;

import alp.club.config.Properties;
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
public class MobileGatewayApplication {

    private static final Logger logger = LoggerFactory.getLogger(MobileGatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MobileGatewayApplication.class, args);
    }

    private final String GATEWAY_HEADER_KEY = "Gateway";
    private final String GATEWAY_HEADER_VALUE = "Mobile";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, Properties properties) {
        logger.info("Release stage: {}", properties.getReleaseStage());

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
                        .filters(f -> f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE))
                        .uri(properties.getEventsUrl())
                )
                .build();
    }

}

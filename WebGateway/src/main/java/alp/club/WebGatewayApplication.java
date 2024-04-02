package alp.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class WebGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class, args);
    }

    private final String GATEWAY_HEADER_KEY = "Gateway";
    private final String GATEWAY_HEADER_VALUE = "Web";

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

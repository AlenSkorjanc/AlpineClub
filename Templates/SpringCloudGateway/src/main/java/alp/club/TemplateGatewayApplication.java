package alp.club;

import alp.club.config.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class TemplateGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateGatewayApplication.class, args);
    }

    private final String GATEWAY_HEADER_KEY = "Gateway";
    private final String GATEWAY_HEADER_VALUE = "Template";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, Properties properties) {
        return builder.routes()
                .route(r -> r.path("/gateway-path/**")
                        .filters(f -> f.addRequestHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE)
                                .addResponseHeader(GATEWAY_HEADER_KEY, GATEWAY_HEADER_VALUE))
                        .uri(properties.getApiUrl())
                )
                .build();
    }

}

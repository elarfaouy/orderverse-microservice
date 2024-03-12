package org.youcode.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/api/v1/product/**")
                        .uri("lb://product-service")
                )
                .route(r -> r
                        .path("/api/v1/order/**")
                        .uri("lb://order-service")
                )
                .route(r -> r
                        .path("/api/v1/inventory/**")
                        .uri("lb://inventory-service")
                )
                .route(r -> r
                        .path("/eureka")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/"))
                        .uri("http://localhost:9979")
                )

                .route(r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:9979")
                )
                .build();
    }
}

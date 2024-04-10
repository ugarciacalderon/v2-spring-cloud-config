package com.ugarciac.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		String red = "ugarciac";
		return builder.routes()
				.route(p ->
						p.path("/ugarciac/accounts/**")
								.filters(f -> f.rewritePath("/ugarciac/accounts/(?<segment>.*)","/${segment}"))
								.uri("lb://ACCOUNTS"))
				.route(p ->
						p.path("/ugarciac/loans/**")
								.filters(f -> f.rewritePath("/ugarciac/loans/(?<segment>.*)","/${segment}"))
								.uri("lb://LOANS"))
				.route(p ->
						p.path("/ugarciac/cards/**")
								.filters(f -> f.rewritePath("/ugarciac/cards/(?<segment>.*)","/${segment}"))
								.uri("lb://CARDS"))
				.build();
	}

}

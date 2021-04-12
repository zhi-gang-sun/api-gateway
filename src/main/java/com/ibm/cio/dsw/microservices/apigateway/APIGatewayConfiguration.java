package com.ibm.cio.dsw.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		// add request header and redirect to other URIs
		return builder.routes()
				.route(p -> p.path("/get").filters(
						f -> f.addRequestHeader("MyHeader", "MyURI").addRequestParameter("MyParm", "MyParmValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://CURRENCY-CONVERSION-SERVICE"))
				.route(p -> p.path("/currency-conversion-feign/**").uri("lb://CURRENCY-CONVERSION-SERVICE"))
				.build();
	}
}

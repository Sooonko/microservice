package com.cloudAPIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsCloudApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudApiGatewayApplication.class, args);
	}

}

package org.galaxy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GalaxyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxyWebApplication.class, args);
	}

}

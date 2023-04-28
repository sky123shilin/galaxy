package org.galaxy.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GalaxyRegistryEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxyRegistryEurekaApplication.class, args);
	}

}

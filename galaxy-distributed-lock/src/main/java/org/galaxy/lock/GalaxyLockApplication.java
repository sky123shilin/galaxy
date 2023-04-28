package org.galaxy.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GalaxyLockApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GalaxyLockApplication.class, args);
		handle(applicationContext);
	}

	public static void handle(ApplicationContext applicationContext) {

	}

}

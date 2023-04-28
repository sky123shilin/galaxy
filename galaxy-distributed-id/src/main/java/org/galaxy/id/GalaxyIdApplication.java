package org.galaxy.id;

import org.galaxy.id.generator.KeyGenerator;
import org.galaxy.id.generator.MongoObjectIdKeyGenerator;
import org.galaxy.id.generator.ObjectIdKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GalaxyIdApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GalaxyIdApplication.class, args);
		handle(applicationContext);
	}

	public static void handle(ApplicationContext applicationContext) {
		KeyGenerator keyGenerator = applicationContext.getBean(MongoObjectIdKeyGenerator.class);

		for (int i = 0; i < 10; i++) {
			System.out.println(keyGenerator.generateId());
		}
	}

}

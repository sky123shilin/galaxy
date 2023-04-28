package org.galaxy.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 分布式任务，涉及quartz、xxl-job、elastic-job
 * 1.定时任务
 * 2.延时任务
 */
@SpringBootApplication
public class GalaxyJobApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GalaxyJobApplication.class, args);
		handle(applicationContext);
	}

	public static void handle(ApplicationContext applicationContext) {

	}

}

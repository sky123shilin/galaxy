package org.galaxy.limit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 分布式令牌桶、漏桶、滑动窗口实现
 */
@SpringBootApplication
public class GalaxyLimitApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GalaxyLimitApplication.class, args);
		handle(applicationContext);
	}

	public static void handle(ApplicationContext applicationContext) {

	}

}

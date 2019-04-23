package com.monali.microserviceapp.webserver.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class WebserverApplication {

	public static final String USER_SERVICE_URL = "http://USER-SERVICE";

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebserverApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {

		return new RestTemplate();
	}

	@Bean
	public WebUserService userService() {

		return new WebUserService(USER_SERVICE_URL);
	}


	@Bean
	public WebUserController userController() {

		return new WebUserController(userService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
}
